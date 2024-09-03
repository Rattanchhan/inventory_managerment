package com.inventory_managerment.feature.customer.dto;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
@Builder
public record CustomerUpdateRequest(
        String name,
        String phone,
        @Positive(message = "Must be positive number")
        Integer point
) {
}
