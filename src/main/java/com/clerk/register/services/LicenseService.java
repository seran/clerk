package com.clerk.register.services;

import com.clerk.register.data.requests.BatchLicenseRequest;
import com.clerk.register.data.requests.LicenseCreateRequest;
import com.clerk.register.data.responses.LicenseResponse;
import com.clerk.register.exceptions.ResourceNotFoundException;
import com.clerk.register.models.License;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.LicenseRepository;
import com.clerk.register.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public List<LicenseResponse> findAll() {
        return licenseRepository
                .findAll()
                .stream()
                .map(LicenseResponse::from).toList();
    }

    public LicenseResponse findById(Long id) {
        return LicenseResponse.from(findLicenseById(id));
    }

    public LicenseResponse patch(Long id, Map<String, Object> changes) throws JacksonException {
        License license = findLicenseById(id);
        objectMapper.updateValue(license, changes);
        return LicenseResponse.from(license);
    }

    private License findLicenseById(Long id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License", id));
    }

    @Transactional
    public LicenseResponse create(LicenseCreateRequest request) {
        License license = new License(request.key(), true);
        license.setProduct(getProductById(request.productId()));
        license.setUserId(request.userId());
        return LicenseResponse.from(licenseRepository.save(license));
    }

    @Transactional
    public int createBatch(BatchLicenseRequest request) {
        Product product = getProductById(request.productId());

        List<License> batch = new ArrayList<>(request.count());
        for (int i = 0; i < request.count(); i++) {
            License license = new License(request.prefixOrDefault() + "-" + i, true);
            license.setProduct(product);
            batch.add(license);
        }

        return licenseRepository.saveAll(batch).size();
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }
}
