package com.clerk.register.data.responses;

public record ValidationResponse (boolean valid, String message) {
    public static ValidationResponse accepted() {
        return new ValidationResponse(true, "Valid license");
    }

    public static ValidationResponse rejected(String message) {
        return new ValidationResponse(false, "Invalid license");
    }
}
