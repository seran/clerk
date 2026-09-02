package com.clerk.register.controllers;

import com.clerk.register.data.responses.LoginResponse;
import com.clerk.register.data.responses.UserResponseLegacy;
import com.clerk.register.security.ClerkUserPrincipal;
import com.clerk.register.services.AuthService;
import com.clerk.register.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/legacy/auth")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "clerk.api", name = "legacy-enabled", havingValue = "true")
@Deprecated(since = "2.1")
public class LegacyAuthController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/token")
    @Operation(deprecated = true)
    public LoginResponse token(@RequestParam("username") String username, @RequestParam("password") String password) {
        return authService.issueFor(userService.findByUsername(username));
    }

    @GetMapping("/me")
    @Operation(deprecated = true)
    @PreAuthorize("isAuthenticated()")
    public UserResponseLegacy me(@AuthenticationPrincipal ClerkUserPrincipal clerkUserPrincipal) {
        return UserResponseLegacy.from(clerkUserPrincipal.user());
    }
}
