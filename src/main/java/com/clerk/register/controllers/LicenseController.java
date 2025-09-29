package com.clerk.register.controllers;

import com.clerk.register.data.requests.DeleteItemRequest;
import com.clerk.register.data.requests.LicenseCreateRequest;
import com.clerk.register.models.License;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.LicenseRepository;
import com.clerk.register.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path = "/")
    public ResponseEntity<String> createLicense(@RequestBody LicenseCreateRequest request) {
        Product product = productRepository.findById(request.ProductId).orElse(null);

        if (product != null) {
            License license = new License(request.key, true);
            license.setProduct(product);

            return ResponseEntity.ok().body(
                    licenseRepository
                            .save(license)
                            .getId()
                            .toString()
            );
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/{id}")
    public License getLicense(@PathVariable Long id) {
        return licenseRepository.findById(id).orElse(null);
    }

    @GetMapping(path = "/all")
    public List<License> getLicense() {
        return licenseRepository.findAll();
    }

    @PatchMapping("/")
    public License updateLicense(@RequestBody License license) {
        return licenseRepository.save(license);
    }

    @DeleteMapping(path = "/")
    public void deleteLicense(@RequestBody DeleteItemRequest request) {
        licenseRepository.deleteById(request.id);
    }
}
