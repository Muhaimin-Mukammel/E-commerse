package com.auth.authservice.service;

import com.auth.authservice.dto.AuthResponse;
import com.auth.authservice.dto.LoginRequest;
import com.auth.authservice.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    AuthResponse login(@Valid @RequestBody LoginRequest request);
    AuthResponse register(@Valid @RequestBody RegisterRequest request);
}
