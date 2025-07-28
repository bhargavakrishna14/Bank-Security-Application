package dev.bhargav.banksecurity.user;

import dev.bhargav.banksecurity.dto.JwtRequest;
import dev.bhargav.banksecurity.dto.JwtResponse;
import dev.bhargav.banksecurity.dto.UserDto;
import dev.bhargav.banksecurity.service.AuthService;
import dev.bhargav.banksecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody JwtRequest jwtRequest
    ) {
        return new ResponseEntity<>(authService.login(jwtRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user) {
        userService.registerUser(user);
    }
}
