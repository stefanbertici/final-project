package ro.ubb.postuniv.musify.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.postuniv.musify.dto.UserDto;
import ro.ubb.postuniv.musify.dto.UserLoginDto;
import ro.ubb.postuniv.musify.dto.UserLoginViewDto;
import ro.ubb.postuniv.musify.dto.UserViewDto;
import ro.ubb.postuniv.musify.service.UserService;
import ro.ubb.postuniv.musify.utils.constants.UserRole;
import ro.ubb.postuniv.musify.utils.constants.UserStatus;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserViewDto>> readAll() {
        List<UserViewDto> users = userService.readAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDto> readById(@PathVariable Integer id) {
        UserViewDto user = userService.readUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> register(@RequestBody @Valid UserDto userDto) {
        UserViewDto user = userService.register(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginViewDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        UserLoginViewDto userLoginViewDto = userService.login(userLoginDto);
        return new ResponseEntity<>(userLoginViewDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = "Authorization") String header) {
        String response = userService.logout(header);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserViewDto> update(@PathVariable Integer id, @RequestBody @Valid UserDto userDto) {
        UserViewDto user = userService.update(id, userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<UserViewDto> promote(@PathVariable Integer id) {
        UserViewDto user = userService.updateUserRole(id, UserRole.ADMIN);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/demote")
    public ResponseEntity<UserViewDto> demote(@PathVariable Integer id) {
        UserViewDto user = userService.updateUserRole(id, UserRole.USER);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UserViewDto> activate(@PathVariable Integer id) {
        UserViewDto user = userService.updateUserStatus(id, UserStatus.ACTIVE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserViewDto> deactivate(@PathVariable Integer id) {
        UserViewDto user = userService.updateUserStatus(id, UserStatus.INACTIVE);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserViewDto> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }
}
