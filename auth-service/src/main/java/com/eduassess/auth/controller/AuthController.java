package com.eduassess.auth.controller;

import com.eduassess.auth.dto.*;
import com.eduassess.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service){this.service=service;}

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(){
        return ResponseEntity.ok(java.util.Map.of("valid", true, "message", "JWT is valid"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> badRequest(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
    }
}
