package com.clerk.register.data.requests;


import jakarta.validation.constraints.NotBlank;

public record ProductImageRequest(
        @NotBlank String url,
        Long productId
) {
}
