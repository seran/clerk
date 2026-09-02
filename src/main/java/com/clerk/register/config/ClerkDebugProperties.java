package com.clerk.register.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clerk.debug")
public record ClerkDebugProperties(boolean includeStackTrace) {
}
