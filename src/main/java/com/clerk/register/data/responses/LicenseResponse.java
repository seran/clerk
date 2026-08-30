package com.clerk.register.data.responses;

import com.clerk.register.models.License;

public record LicenseResponse(
        Long id,
        String licenseKey,
        Boolean active,
        Long productId,
        Long userId,
        String activationSecret) {

    public static LicenseResponse from(License license) {
        return new LicenseResponse(
                license.getId(),
                license.getLicenseKey(),
                license.getActive(),
                license.getProduct() != null ? license.getProduct().getId() : null,
                license.getUserId(),
                license.getActivationSecret()
        );
    }
}
