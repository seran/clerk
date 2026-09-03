package com.clerk.register.e2e;

import com.clerk.register.common.ClerkE2ETestBase;
import com.clerk.register.data.requests.LicenseCreateRequest;
import com.clerk.register.data.responses.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class LicenseTest extends ClerkE2ETestBase {
    @Test
    void shouldCreateLicense() {
        String token = tokenFor(ALICE, ALICE_PASSWORD);

        ProductResponse product = rest.get().uri("/api/product/all")
                .headers(bearer(token))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponse[].class)
                .returnResult()
                .getResponseBody()[0];


        rest.post().uri("/api/license/")
                .headers(bearer(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LicenseCreateRequest(product.id(), "ALICE-0001-TEST", 1L))
                .exchange()
                .expectStatus()
                .isCreated();
    }
}
