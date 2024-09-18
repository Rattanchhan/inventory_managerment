package com.inventory_managerment.feature.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
    String message,
    String email
) {
    
}
