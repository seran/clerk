package com.clerk.register.data.requests;

import com.clerk.register.models.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateRequest(@NotNull Role role) {
}
