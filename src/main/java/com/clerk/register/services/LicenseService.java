package com.clerk.register.services;

import com.clerk.register.data.responses.LicenseResponse;
import com.clerk.register.exceptions.ResourceNotFoundException;
import com.clerk.register.models.License;
import com.clerk.register.repositories.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ObjectMapper objectMapper;

    public List<License> findAll() {
        return licenseRepository.findAll();
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



}
