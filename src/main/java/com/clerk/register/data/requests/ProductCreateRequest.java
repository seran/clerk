package com.clerk.register.data.requests;

import jakarta.validation.constraints.NotBlank;

public record ProductCreateRequest (
        @NotBlank String name,
        String description,
        Boolean active
) {
}
