package ro.ubb.postuniv.musify.service;

import static ro.ubb.postuniv.musify.utils.checkers.UserChecker.*;
import static ro.ubb.postuniv.musify.utils.constants.UserRole.*;
import static ro.ubb.postuniv.musify.utils.constants.UserStatus.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
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
import ro.ubb.postuniv.musify.repository.PlaylistRepository;
import ro.ubb.postuniv.musify.repository.UserRepository;
import ro.ubb.postuniv.musify.security.InMemoryTokenBlacklist;
import ro.ubb.postuniv.musify.security.JwtService;
import ro.ubb.postuniv.musify.utils.checkers.RepositoryChecker;
import ro.ubb.postuniv.musify.utils.constants.UserRole;
import ro.ubb.postuniv.musify.utils.constants.UserStatus;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final RepositoryChecker repositoryChecker;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final UserMapper userMapper;
    private final InMemoryTokenBlacklist inMemoryTokenBlacklist;

    @Transactional
    public User readCurrentUser() {
        return repositoryChecker.getUserIfExists(jwtService.getCurrentUserId());
    }

    @Transactional
    public List<UserViewDto> readAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toViewDtos(users);
    }

    @Transactional
    public UserViewDto readUserById(int id) {
        User user = repositoryChecker.getUserIfExists(id);
        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDto register(UserDto userDto) {
        Optional<User> optional = userRepository.findUserByEmail(userDto.getEmail());
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Email " + userDto.getEmail() + " is already registered");
        }

        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);

        String encryptedPassword = getEncryptedPassword(userDto.getPassword());
        user.setEncryptedPassword(encryptedPassword);
        user.setRole(USER.value);
        user.setStatus(ACTIVE.value);

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserLoginViewDto login(UserLoginDto userLoginDto) {
        Optional<User> optional = userRepository.findUserByEmail(userLoginDto.getEmail());
        String encryptedInputPassword = getEncryptedPassword(userLoginDto.getPassword());

        if (optional.isEmpty()) {
            throw new UnauthorizedException("Incorrect email or password");
        }

        User user = optional.get();
        if (!canLogin(user, encryptedInputPassword)) {
            throw new UnauthorizedException("Incorrect email or password");
        }

        if (!isActive(user)) {
            throw new UnauthorizedException("It looks like your account has been deactivated :(\n Please contact our customer support department");
        }

        String accessToken = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole());
        return userMapper.toLoginViewDto(user, accessToken);
    }

    public String logout(String header) {
        String token = jwtService.extractTokenFromHeader(header);
        inMemoryTokenBlacklist.blacklist(token);
        return "Logout successful";
    }

    @Transactional
    public UserViewDto update(Integer id, UserDto userDto) {
        User user = repositoryChecker.getUserIfExists(id);

        repositoryChecker.checkIfEmailIsTaken(id, userDto.getEmail());

        if (isCurrentUserNotAdmin(jwtService.getCurrentUserRole()) && !isOperationOnSelf(jwtService.getCurrentUserId(), id)) {
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
        validateAdminRole(jwtService.getCurrentUserRole());
        if (jwtService.getCurrentUserId().equals(id)) {
            throw new IllegalArgumentException("You cannot modify your own role");
        }

        User user = repositoryChecker.getUserIfExists(id);

        if (role == ADMIN) {
            user.setRole(ADMIN.value);
        } else if (role == USER) {
            user.setRole(USER.value);
        }

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDto updateUserStatus(Integer id, UserStatus status) {
        validateAdminRole(jwtService.getCurrentUserRole());
        if (jwtService.getCurrentUserId().equals(id)) {
            throw new IllegalArgumentException("You cannot modify your own status");
        }

        User user = repositoryChecker.getUserIfExists(id);

        if (status == ACTIVE) {
            user.setStatus(ACTIVE.value);
        } else if (status == INACTIVE) {
            user.setStatus(INACTIVE.value);
        }

        return userMapper.toViewDto(user);
    }

    @Transactional
    public UserViewDto delete(Integer id) {
        validateAdminRole(jwtService.getCurrentUserRole());

        User user = repositoryChecker.getUserIfExists(id);

        user.getOwnedPlaylists().forEach(playlist -> {
            playlist.getFollowerUsers().forEach(follower -> follower.removeFollowedPlaylist(playlist));
            playlistRepository.delete(playlist);
        });

        userRepository.delete(user);

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
