package com.eduassess.auth.dto;
public record AuthResponse(String token, Long userId, String email, String role, String message) {}
