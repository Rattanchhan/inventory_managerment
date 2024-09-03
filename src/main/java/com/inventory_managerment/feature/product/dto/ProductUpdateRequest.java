package com.inventory_managerment.feature.product.dto;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductUpdateRequest(
        @NotNull(message="Name required")
        String name,
        @NotNull(message = "Stock Quantity required")
        @Positive(message = "Must be positive number")
        Integer stockQty,
        @NotNull(message = "Unit price is required")
        @Positive(message = "Must be positive number")
        BigDecimal unitPrice,
        String photo,
        String description,
        String status,
        String type
) {
}
