package com.clerk.register.services;

import com.clerk.register.data.responses.LicenseResponse;
import com.clerk.register.exceptions.ResourceNotFoundException;
import com.clerk.register.models.License;
import com.clerk.register.repositories.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    public LicenseResponse findById(Long id) {
        return LicenseResponse.from(
                licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License", id))
        );
    }

}
