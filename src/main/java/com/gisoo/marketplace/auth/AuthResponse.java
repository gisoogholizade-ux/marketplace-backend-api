package com.gisoo.marketplace.auth;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds,
        Long userId,
        String email,
        String role
) {}
