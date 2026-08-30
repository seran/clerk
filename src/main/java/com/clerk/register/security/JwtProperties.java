package com.clerk.register.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clerk.jwt")
public record JwtProperties(String secret, boolean acceptUnsignedTokens) {
}
