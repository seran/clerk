package com.clerk.register.controllers;

import com.clerk.register.data.requests.BatchLicenseRequest;
import com.clerk.register.data.requests.DeleteItemRequest;
import com.clerk.register.data.requests.LicenseCreateRequest;
import com.clerk.register.data.responses.LicenseResponse;
import com.clerk.register.models.License;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.LicenseRepository;
import com.clerk.register.repositories.ProductRepository;
import com.clerk.register.services.LicenseService;
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
@RequestMapping("/api/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseRepository licenseRepository;

    private final ProductRepository productRepository;
    private final LicenseService licenseService;

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public LicenseResponse createLicense(@Valid @RequestBody LicenseCreateRequest request) {
        return licenseService.create(request);
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

    @PostMapping("/batch")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> createBatchLicense(@Valid @RequestBody BatchLicenseRequest request) {
        return ResponseEntity.ok(licenseService.createBatch(request) + " licenses crated");
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
