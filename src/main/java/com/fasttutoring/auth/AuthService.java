package com.fasttutoring.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    UserEntity register(String email, String password) {
        List<String> userRoles = new LinkedList<String>();
        userRoles.add("Role");
        String hashedPassword = passwordEncoder.encode(password);

        UserEntity user = new UserEntity();

        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRoles(userRoles);

        UserEntity createdUser = userRepository.save(user);
        return createdUser;
    }

    URI getUserUri(UserEntity user) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/:id")
                .buildAndExpand(user.getId())
                .toUri();
        return uri;
    }
}
