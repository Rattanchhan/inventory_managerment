package com.inventory_managerment.feature.product.dto;
import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String name,
        Integer stockQty,
        BigDecimal unitPrice,
        String photo,
        String description,
        String status,
        String type
) {
}
