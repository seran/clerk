package com.clerk.register.data.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BatchLicenseRequest(
    @NotNull Long productId,
    @Min(1) int count,
    String prefix) {

    public String prefixOrDefault() {
        return prefix != null ? prefix : "BULK";
    }
}
