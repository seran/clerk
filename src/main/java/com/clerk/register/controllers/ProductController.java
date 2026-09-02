package com.clerk.register.controllers;

import com.clerk.register.data.requests.ProductCreateRequest;
import com.clerk.register.data.requests.ProductImageRequest;
import com.clerk.register.data.responses.ProductResponse;
import com.clerk.register.models.Product;
import com.clerk.register.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.JacksonException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping(path = "/all")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    @PatchMapping(path = "/")
    @PreAuthorize("isAuthenticated()")
    public ProductResponse updateProduct(
            @PathVariable("id") Long id,
            Map<String, Object> changes
    ) throws JacksonException {
        return productService.patch(id, changes);
    }

    @DeleteMapping(path = "/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/fetch/image")
    public ResponseEntity uploadImage(@RequestBody ProductImageRequest request) {
        if (request.url != null) {
            try {
                URL url = new URI(request.url).toURL();

                logger.info("Calling {}", request.url);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                // Timeout must be 1 second to avoid long hanging requests
                connection.setReadTimeout(1000);

                logger.info("Response status code " + connection.getResponseCode());

                if (connection.getResponseCode() == 200) {
                    Product product = productService.findProductById(request.productId);

                    if (product != null) {
                        product.setImageURL(request.url);
                        productService.updateProduct(product);

                        return ResponseEntity.status(200).body("OK");
                    }
                }

                return ResponseEntity.status(422).body("Unable to fetch.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.badRequest().build();
    }

}
