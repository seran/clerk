package com.clerk.register.data.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LicenseCreateRequest(
        @NotNull Long productId,
        @NotBlank String key,
        Long userId
) {
}
