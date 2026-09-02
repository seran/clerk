package com.clerk.register.controllers;

import com.clerk.register.data.requests.DeleteItemRequest;
import com.clerk.register.data.requests.LicenseCreateRequest;
import com.clerk.register.data.responses.LicenseResponse;
import com.clerk.register.models.License;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.LicenseRepository;
import com.clerk.register.repositories.ProductRepository;
import com.clerk.register.services.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.JacksonException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseRepository licenseRepository;

    private final ProductRepository productRepository;
    private final LicenseService licenseService;

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

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public LicenseResponse getLicense(@PathVariable("id") Long id) {
        return licenseService.findById(id);
    }

    @GetMapping(path = "/all")
    @PreAuthorize("isAuthenticated()")
    public List<License> getLicense() {
        return licenseRepository.findAll();
    }

    @PatchMapping("/")
    @PreAuthorize("isAuthenticated()")
    public LicenseResponse updateLicense(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> changes
    ) throws JacksonException {
        return licenseService.patch(id, changes);
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<?> deleteLicense(@RequestBody DeleteItemRequest request) {
        licenseRepository.deleteById(request.id);
        return ResponseEntity.ok().build();
    }
}
