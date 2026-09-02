package com.clerk.register.data.responses;

import com.clerk.register.models.Role;
import com.clerk.register.models.User;

public record UserResponseLegacy(
        Long id,
        String username,
        String password,
        String hashed_password,
        String email,
        Role role
) {
    public static UserResponseLegacy from(User user) {
        return new UserResponseLegacy(user.getId(), user.getUsername(), user.getPassword(), user.getHashed_password(), user.getEmail(), user.getRole());
    }
}
