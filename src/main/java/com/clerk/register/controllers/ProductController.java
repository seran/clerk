package com.clerk.register.controllers;

import com.clerk.register.data.requests.ProductCreateRequest;
import com.clerk.register.data.requests.ProductRemoteFetchRequest;
import com.clerk.register.data.responses.ProductResponse;
import com.clerk.register.models.Product;
import com.clerk.register.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.JacksonException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping(path = "/all")
    public List<ProductResponse> getProducts() {
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
    public ResponseEntity<String> fetchImage(@Valid @RequestBody ProductRemoteFetchRequest request) {
        return productService.fetchImage(request);
    }

    @PostMapping("/import/metadata")
    public ProductResponse importMetadata(@Valid @RequestBody ProductRemoteFetchRequest request) throws JacksonException {
        return productService.importMetadata(request);
    }
}
