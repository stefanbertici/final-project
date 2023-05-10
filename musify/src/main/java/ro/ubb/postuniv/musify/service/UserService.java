package ro.ubb.postuniv.musify.service;

import ro.ubb.postuniv.musify.dto.UserLoginViewDTO;
import ro.ubb.postuniv.musify.mapper.UserMapper;
import ro.ubb.postuniv.musify.dto.UserDTO;
import ro.ubb.postuniv.musify.dto.UserLoginDTO;
import ro.ubb.postuniv.musify.dto.UserViewDTO;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.model.User;
import ro.ubb.postuniv.musify.repository.UserRepository;
import ro.ubb.postuniv.musify.security.InMemoryTokenBlacklist;
import ro.ubb.postuniv.musify.security.JwtUtils;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;
import ro.ubb.postuniv.musify.utils.UserChecker;
import ro.ubb.postuniv.musify.utils.UserRole;
import ro.ubb.postuniv.musify.utils.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final RepositoryChecker repositoryChecker;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InMemoryTokenBlacklist inMemoryTokenBlacklist;

    @Transactional
    public List<UserViewDTO> readAllUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toViewDtos(users);
    }

    @Transactional
    public UserViewDTO readUserById(int id) {
        User user = repositoryChecker.getUserIfExists(id);

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDTO registerUser(UserDTO userDTO) {
        Optional<User> optional = userRepository.findUserByEmail(userDTO.getEmail());
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Email " + userDTO.getEmail() + " is already registered");
        }

        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);

        String encryptedPassword = getEncryptedPassword(userDTO.getPassword());
        user.setEncryptedPassword(encryptedPassword);
        user.setRole("user");
        user.setStatus("active");

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserLoginViewDTO loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> optional = userRepository.findUserByEmail(userLoginDTO.getEmail());
        String encryptedInputPassword = getEncryptedPassword(userLoginDTO.getPassword());

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
    public UserViewDTO updateUser(Integer id, UserDTO userDTO) {
        User user = repositoryChecker.getUserIfExists(id);

        if (UserChecker.isCurrentUserNotAdmin() && !UserChecker.isOperationOnSelf(id)) {
            throw new UnauthorizedException("Users can only update their own info");
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        String encryptedPassword = getEncryptedPassword(userDTO.getPassword());
        user.setEncryptedPassword(encryptedPassword);
        user.setCountry(userDTO.getCountry());

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDTO updateUserRole(Integer id, UserRole role) {
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
    public UserViewDTO updateUserStatus(Integer id, UserStatus status) {
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
