package com.clerk.register.intelligence;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

final class ClerkPrompts {
    static final Resource SYSTEM = prompt("system");

    static final Resource VERIFY_DESCRIPTION = prompt("verify_description");

    private ClerkPrompts() {}

    private static Resource prompt(String name) {
        return new ClassPathResource("prompts/" + name + ".txt");
    }
}
