package io.noshield.controllers;

import io.noshield.models.LicenseModel;
import io.noshield.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private LicenseRepository licenseRepository;

    @PostMapping(path = "/create")
    public LicenseModel createLicense(@RequestBody LicenseModel licenseModel) {
        return licenseRepository.save(licenseModel);
    }
}
