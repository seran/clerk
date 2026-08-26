package com.clerk.register.controllers;

import com.clerk.register.data.responses.ValidationResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
public class ValidationController {

    @GetMapping("/validate/{token}")
    public ValidationResponse validateLicense(@PathVariable("token") String token) {
        return ValidationResponse.accepted();
    }

}
