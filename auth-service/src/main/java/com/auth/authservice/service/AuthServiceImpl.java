package com.auth.authservice.service;

import com.auth.authservice.dto.AuthResponse;
import com.auth.authservice.dto.LoginRequest;
import com.auth.authservice.dto.RegisterRequest;
import com.auth.authservice.dto.RegisterResponse;
import com.auth.authservice.entities.User;
import com.auth.authservice.enums.Role;
import com.auth.authservice.enums.Status;
import com.auth.authservice.repository.UserRepository;
import com.auth.authservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.pass())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        String token = jwtTokenProvider.generateToken(user);

        return new AuthResponse(token, "Bearer", user.getRole().toString(), user.getEmail());
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        if(request.role() == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot register as ADMIN");
        }

        User user = new User(request.email(), request.username(), passwordEncoder.encode(request.password()), request.role(), Status.PENDING);

        userRepository.save(user);

        return new RegisterResponse(user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
