package com.inventory_managerment.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank(message = "Phone Number is required")
    String phoneNumber,
    @NotBlank(message = "Email is required")
    String email,
    @NotBlank(message = "Password is required")
    String password,
    @NotBlank(message = "Confirm Password is required")
    String confirmPassword,
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Gender is required")
    String gender
    ){

    }
