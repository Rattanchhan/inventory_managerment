package com.inventory_managerment.feature.customer.dto;
import lombok.Builder;

@Builder
public record CustomerResponse(
        String name,
        String phone,
        Integer point
) {
}
