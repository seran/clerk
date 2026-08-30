package com.clerk.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ClerkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClerkApplication.class,args);
    }
}
