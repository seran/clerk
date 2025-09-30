package com.clerk.register.controllers;

import com.clerk.register.data.responses.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
