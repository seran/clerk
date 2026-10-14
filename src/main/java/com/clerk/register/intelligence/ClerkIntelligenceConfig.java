package com.clerk.register.intelligence;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "clerk.intelligence", name = "enabled", havingValue = "true")
public class ClerkIntelligenceConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(ClerkPrompts.SYSTEM)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
