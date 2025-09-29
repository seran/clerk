package io.noshield.data.requests;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductImageRequest {
    @Schema(
            name = "url",
            example = "https://example.com/data/json",
            description = "Remote URL to fetch image."
    )
    public String url;

    public Long productId;
}
