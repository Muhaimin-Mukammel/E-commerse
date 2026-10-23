package com.auth.authservice.dto;

public record RegisterResponse(
        String name,
        String email,
        String role
) {
}
