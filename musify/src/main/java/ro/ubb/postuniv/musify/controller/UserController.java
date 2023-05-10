package ro.ubb.postuniv.musify.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.UserDTO;
import ro.ubb.postuniv.musify.dto.UserLoginDTO;
import ro.ubb.postuniv.musify.dto.UserLoginViewDTO;
import ro.ubb.postuniv.musify.dto.UserViewDTO;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.UserService;
import ro.ubb.postuniv.musify.utils.UserChecker;
import ro.ubb.postuniv.musify.utils.UserRole;
import ro.ubb.postuniv.musify.utils.UserStatus;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserViewDTO>> readAllUsers() {
        List<UserViewDTO> users = userService.readAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDTO> readUserById(@PathVariable Integer id) {
        UserViewDTO user = userService.readUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        UserViewDTO user = userService.registerUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginViewDTO> loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        UserLoginViewDTO userLoginViewDTO = userService.loginUser(userLoginDTO);
        return new ResponseEntity<>(userLoginViewDTO, HttpStatus.OK);
    }

    @GetMapping("/403")
    public ResponseEntity<String> unauthenticated() {
        return new ResponseEntity<>("Please log in first", HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader(name = "Authorization") String header) {
        String response = userService.logoutUser(header);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserViewDTO> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDTO userDTO) {
        UserViewDTO user = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<UserViewDTO> promoteUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user roles");
        }

        UserViewDTO user = userService.updateUserRole(id, UserRole.ADMIN);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/demote")
    public ResponseEntity<UserViewDTO> demoteUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user roles");
        }

        UserViewDTO user = userService.updateUserRole(id, UserRole.USER);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UserViewDTO> activateUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user statuses");
        }

        UserViewDTO user = userService.updateUserStatus(id, UserStatus.ACTIVE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserViewDTO> deactivateUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user statuses");
        }

        UserViewDTO user = userService.updateUserStatus(id, UserStatus.INACTIVE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
