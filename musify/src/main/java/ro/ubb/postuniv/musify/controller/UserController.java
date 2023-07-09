package ro.ubb.postuniv.musify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.postuniv.musify.dto.UserDto;
import ro.ubb.postuniv.musify.dto.UserLoginDto;
import ro.ubb.postuniv.musify.dto.UserLoginViewDto;
import ro.ubb.postuniv.musify.dto.UserViewDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.service.UserService;
import ro.ubb.postuniv.musify.utils.UserChecker;
import ro.ubb.postuniv.musify.utils.UserRole;
import ro.ubb.postuniv.musify.utils.UserStatus;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserViewDto>> readAllUsers() {
        List<UserViewDto> users = userService.readAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDto> readUserById(@PathVariable Integer id) {
        UserViewDto user = userService.readUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserViewDto user = userService.registerUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginViewDto> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        UserLoginViewDto userLoginViewDto = userService.loginUser(userLoginDto);
        return new ResponseEntity<>(userLoginViewDto, HttpStatus.OK);
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
    public ResponseEntity<UserViewDto> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDto userDto) {
        UserViewDto user = userService.updateUser(id, userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<UserViewDto> promoteUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user roles");
        }

        UserViewDto user = userService.updateUserRole(id, UserRole.ADMIN);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/demote")
    public ResponseEntity<UserViewDto> demoteUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user roles");
        }

        UserViewDto user = userService.updateUserRole(id, UserRole.USER);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UserViewDto> activateUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user statuses");
        }

        UserViewDto user = userService.updateUserStatus(id, UserStatus.ACTIVE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserViewDto> deactivateUser(@PathVariable Integer id) {
        if (UserChecker.isCurrentUserNotAdmin()) {
            throw new UnauthorizedException("Only admins can modify user statuses");
        }

        UserViewDto user = userService.updateUserStatus(id, UserStatus.INACTIVE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
