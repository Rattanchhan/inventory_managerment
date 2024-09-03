package com.inventory_managerment.feature.customer.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerRequest(
        @NotBlank
        String name,
        @NotBlank(message="Phone number is required")
        @Size(min=9,max=10,message = "Phone number must be between 9 to 10 digits")
        String phone,
        @Positive(message = "Must be positive number")
        @NotNull
        Integer point
) {
}
