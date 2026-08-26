package com.clerk.register.data.responses;

import com.clerk.register.models.Product;

public record ProductResponse (
        Long id,
        String name,
        String description,
        String imageURL,
        Boolean active,
        Long ownerId) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImageURL(),
                product.getActive(),
                product.getOwnerId()
        );
    }

}
