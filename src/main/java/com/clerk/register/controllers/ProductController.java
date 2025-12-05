package com.clerk.register.controllers;

import com.clerk.register.data.requests.DeleteItemRequest;
import com.clerk.register.data.requests.ProductImageRequest;
import com.clerk.register.models.Product;
import com.clerk.register.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping(path = "/all")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping(path = "/")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<String> deleteProduct(@RequestBody DeleteItemRequest request) {
        productService.deleteProduct(request.id);
        return ResponseEntity.ok("Deleted");
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
                    Product product = productService.getProductById(request.productId);

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
