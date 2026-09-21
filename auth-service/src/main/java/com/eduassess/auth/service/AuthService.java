package com.eduassess.auth.service;

import com.eduassess.auth.dto.*;
import com.eduassess.auth.entity.Role;
import com.eduassess.auth.entity.User;
import com.eduassess.auth.repository.UserRepository;
import com.eduassess.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwtService) {
        this.users=users; this.encoder=encoder; this.jwtService=jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (users.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User user = users.save(new User(null, request.fullName(), request.email(),
                encoder.encode(request.password()), Role.STUDENT));
        String token = jwtService.generateToken(String.valueOf(user.getId()), user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole().name(), "Registration successful");
    }

    public AuthResponse login(LoginRequest request) {
        User user = users.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtService.generateToken(String.valueOf(user.getId()), user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole().name(), "Login successful");
    }
}
