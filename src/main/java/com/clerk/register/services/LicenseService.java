package com.clerk.register.services;

import com.clerk.register.models.License;
import com.clerk.register.repositories.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public List<License> findAll() {
        return licenseRepository.findAll();
    }

}
