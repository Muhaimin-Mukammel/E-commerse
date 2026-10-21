package com.auth.authservice.dto;

import com.auth.authservice.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull
        @Email
        String email,

        @NotNull
        String password,

        @NotNull
        String username,

        @NotNull
        Role role
) {
}
