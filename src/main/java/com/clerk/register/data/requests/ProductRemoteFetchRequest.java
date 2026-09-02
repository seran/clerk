package com.clerk.register.data.requests;


import jakarta.validation.constraints.NotBlank;

public record ProductRemoteFetchRequest(
        @NotBlank String url,
        Long productId
) {
}
