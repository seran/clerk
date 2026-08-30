package com.clerk.register.data.responses;

public record LoginResponse(
        String token,
        Long userId,
        String role
) {
}
