package io.noshield.controllers;

import io.noshield.data.responses.ValidationResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
public class ValidationController {

    @GetMapping("/validate/{token}")
    public ValidationResponse validateLicense(@PathVariable("token") String token) {
        ValidationResponse response = new ValidationResponse();
        response.valid = true;
        response.message = "Valid license";

        return response;
    }
}
