package io.noshield.controllers;

import io.noshield.models.ProductModel;
import io.noshield.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/create")
    public ProductModel createProduct(@RequestBody ProductModel productModel) {
        return productRepository.save(productModel);
    }

    @GetMapping(path = "/product/{id}")
    public ProductModel getProductById( @PathVariable("id") Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PatchMapping(path = "/product")
    public ProductModel updateProduct(@RequestBody ProductModel productModel) {
        return productRepository.save(productModel);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody ProductModel productModel) {
        productRepository.delete(productModel);
        return ResponseEntity.ok("Deleted");
    }

}
