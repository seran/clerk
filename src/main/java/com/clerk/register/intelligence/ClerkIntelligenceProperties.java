package com.clerk.register.intelligence;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clerk.intelligence")
public record ClerkIntelligenceProperties(boolean enabled) {

}
