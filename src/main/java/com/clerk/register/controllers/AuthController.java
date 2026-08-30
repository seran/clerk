package com.clerk.register.controllers;

import com.clerk.register.data.requests.LoginRequest;
import com.clerk.register.data.responses.LoginResponse;
import com.clerk.register.data.responses.UserResponse;
import com.clerk.register.security.ClerkUserPrincipal;
import com.clerk.register.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponse me(@AuthenticationPrincipal ClerkUserPrincipal clerkUserPrincipal) {
        return UserResponse.from(clerkUserPrincipal.user());
    }
}
