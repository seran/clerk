package com.clerk.register.llm;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clerk.llm")
public record ClerkLlmProperties(
        String baseUrl,
        String model,
        int timeoutMs
) {
}
