package com.clerk.register.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clerk.api")
public record ClerkApiProperties(
        String version,
        boolean legacyEnabled) {
}
