package com.clerk.register.controllers;

import com.clerk.register.data.requests.RoleUpdateRequest;
import com.clerk.register.data.responses.LicenseResponse;
import com.clerk.register.data.responses.UserResponse;
import com.clerk.register.services.LicenseService;
import com.clerk.register.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AdminController {
    private final UserService userService;
    private final LicenseService licenseService;

    @GetMapping("/users")
    public List<UserResponse> listUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/users/{id}/role")
    public UserResponse setRole(@PathVariable("id") Long id, @Valid @RequestBody RoleUpdateRequest request) {
        return userService.updateRole(id, request.role());
    }

    @GetMapping("/licenses")
    public List<LicenseResponse> listLicenses() {
        return licenseService.findAll();
    }
}
