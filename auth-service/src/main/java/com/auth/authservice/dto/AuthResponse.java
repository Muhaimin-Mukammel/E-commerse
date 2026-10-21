package com.auth.authservice.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        String role,
        String email
){}
