package com.fasttutoring.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    String getRegister() {
        return "register";
    }


    @PostMapping("/register")
    @ResponseBody
    ResponseEntity<UserEntity> postRegister(@RequestBody UserEntity user) {
        UserEntity createdUser = this.authService.register(user.getEmail(), user.getPassword());
        URI savedUserUri = this.authService.getUserUri(user);
        return ResponseEntity.created(savedUserUri).body(createdUser);

    }


    @GetMapping("/login")
    String getLogin() {
        return "login";
    }
}
