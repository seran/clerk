package com.clerk.register.controllers;

import com.clerk.register.data.requests.DeleteItemRequest;
import com.clerk.register.data.requests.ProductImageRequest;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping(path = "/all")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PatchMapping(path = "/")
    public Product updateProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<String> deleteProduct(@RequestBody DeleteItemRequest request) {
        productRepository.deleteById(request.id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestBody ProductImageRequest request) {
        if (request.url != null) {
            try {
                URL url = new URL(request.url);

                logger.info("Calling " + request.url);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                // Timeout must be 1 second to avoid long hanging requests
                connection.setReadTimeout(1000);

                logger.info("Response status code " + connection.getResponseCode());

                if (connection.getResponseCode() == 200) {
                    Product product = productRepository.findById(request.productId).orElse(null);

                    if (product != null) {
                        product.setImageURL(request.url);
                        productRepository.save(product);

                        return ResponseEntity.status(200).body("OK");
                    }
                }

                return ResponseEntity.status(204).body("Unable to fetch.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.badRequest().build();
    }

}
