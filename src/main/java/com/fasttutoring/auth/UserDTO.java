package com.fasttutoring.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotNull
    @Email
    private final String email;

    @NotNull
    @Size(min = 8)
    private final String password;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
