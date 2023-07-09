package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.UserDto;
import ro.ubb.postuniv.musify.dto.UserLoginDto;
import ro.ubb.postuniv.musify.dto.UserLoginViewDto;
import ro.ubb.postuniv.musify.dto.UserViewDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.mapper.UserMapper;
import ro.ubb.postuniv.musify.model.User;
import ro.ubb.postuniv.musify.repository.UserRepository;
import ro.ubb.postuniv.musify.security.InMemoryTokenBlacklist;
import ro.ubb.postuniv.musify.security.JwtUtils;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;
import ro.ubb.postuniv.musify.utils.UserChecker;
import ro.ubb.postuniv.musify.utils.UserRole;
import ro.ubb.postuniv.musify.utils.UserStatus;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RepositoryChecker repositoryChecker;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InMemoryTokenBlacklist inMemoryTokenBlacklist;

    @Transactional
    public List<UserViewDto> readAllUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toViewDtos(users);
    }

    @Transactional
    public UserViewDto readUserById(int id) {
        User user = repositoryChecker.getUserIfExists(id);

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDto registerUser(UserDto userDto) {
        Optional<User> optional = userRepository.findUserByEmail(userDto.getEmail());
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Email " + userDto.getEmail() + " is already registered");
        }

        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);

        String encryptedPassword = getEncryptedPassword(userDto.getPassword());
        user.setEncryptedPassword(encryptedPassword);
        user.setRole("user");
        user.setStatus("active");

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserLoginViewDto loginUser(UserLoginDto userLoginDto) {
        Optional<User> optional = userRepository.findUserByEmail(userLoginDto.getEmail());
        String encryptedInputPassword = getEncryptedPassword(userLoginDto.getPassword());

        if (optional.isEmpty()) {
            throw new UnauthorizedException("Incorrect email or password");
        }

        User user = optional.get();
        if (!UserChecker.canLogin(user, encryptedInputPassword)) {
            throw new UnauthorizedException("Incorrect email or password");
        }

        if (!UserChecker.isActive(user)) {
            throw new UnauthorizedException("It looks like your account has been deactivated :(\n Please contact our customer support department");
        }

        String accessToken = JwtUtils.generateToken(user.getId(), user.getEmail(), user.getRole());
        return userMapper.toLoginViewDto(user, accessToken);
    }

    public String logoutUser(String header) {
        String token = JwtUtils.extractTokenFromHeader(header);
        inMemoryTokenBlacklist.blacklist(token);
        return "Logout successful";
    }

    @Transactional
    public UserViewDto updateUser(Integer id, UserDto userDto) {
        User user = repositoryChecker.getUserIfExists(id);

        repositoryChecker.checkIfEmailIsTaken(id, userDto.getEmail());

        if (UserChecker.isCurrentUserNotAdmin() && !UserChecker.isOperationOnSelf(id)) {
            throw new UnauthorizedException("Users can only update their own info");
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        String encryptedPassword = getEncryptedPassword(userDto.getPassword());
        user.setEncryptedPassword(encryptedPassword);
        user.setCountry(userDto.getCountry());

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDto updateUserRole(Integer id, UserRole role) {
        String newRole = "";

        if (role == UserRole.ADMIN) {
            newRole = "admin";
        } else if (role == UserRole.USER) {
            newRole = "user";
        }

        User user = repositoryChecker.getUserIfExists(id);
        user.setRole(newRole);

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDto updateUserStatus(Integer id, UserStatus status) {
        String newStatus = "";

        if (status == UserStatus.ACTIVE) {
            newStatus = "active";
        } else if (status == UserStatus.INACTIVE) {
            newStatus = "inactive";
        }

        User user = repositoryChecker.getUserIfExists(id);
        user.setStatus(newStatus);

        return userMapper.toViewDto(user);
    }

    private String getEncryptedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedPassword = md.digest(password.getBytes());
            BigInteger bigInteger = new BigInteger(1, hashedPassword);
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
