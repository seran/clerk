package io.noshield.controllers;

import io.noshield.data.requests.DeleteItemRequest;
import io.noshield.models.Product;
import io.noshield.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

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

}
