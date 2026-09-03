package com.clerk.register.common;

import com.clerk.register.data.requests.LoginRequest;
import com.clerk.register.data.responses.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.function.Consumer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class ClerkTestFoundation {
    protected static final String ALICE = "alice";

    protected static final String ALICE_PASSWORD = "password1";

    protected static final String ADMIN = "admin";

    protected static final String ADMIN_PASSWORD = "admin123";

    protected RestTestClient rest;

    protected abstract RestTestClient buildClient();

    @BeforeEach
    void bindApiClient() {
        rest = buildClient();
    }

    protected String tokenFor(String username, String password) {
        LoginResponse login = rest.post().uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LoginRequest(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginResponse.class)
                .returnResult().getResponseBody();
        return login.token();
    }

    protected Consumer<HttpHeaders> bearer(String token) {
        return headers -> headers.setBearerAuth(token);
    }
}
