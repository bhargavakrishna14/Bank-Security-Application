package dev.bhargav.banksecurity.user;

import dev.bhargav.banksecurity.dto.JwtRequest;
import dev.bhargav.banksecurity.dto.JwtResponse;
import dev.bhargav.banksecurity.dto.UserDto;
import dev.bhargav.banksecurity.service.AuthService;
import dev.bhargav.banksecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest)
    {
        return new ResponseEntity<>(authService.login(jwtRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user)
    {
        userService.registerUser(user);
    }
}
