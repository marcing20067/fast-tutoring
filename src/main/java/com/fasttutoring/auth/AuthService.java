package com.fasttutoring.auth;

import org.springframework.beans.factory.annotation.Value;
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
    private final String appUrl;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${app.url}") String appUrl) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUrl = appUrl;
    }

    @Transactional
    UserEntity register(UserDTO dto) {
        List<String> newUserRoles = new LinkedList<String>();
        newUserRoles.add("Role");
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        UserEntity user = new UserEntity();

        user.setEmail(dto.getEmail());
        user.setPassword(hashedPassword);
        user.setRoles(newUserRoles);

        UserEntity createdUser = userRepository.save(user);
        return createdUser;
    }

    URI getUserUri(UserEntity user) {
        URI uri = ServletUriComponentsBuilder.fromUriString(appUrl)
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return uri;
    }
}
