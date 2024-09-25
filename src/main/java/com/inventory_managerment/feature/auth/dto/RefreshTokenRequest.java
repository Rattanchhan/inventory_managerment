package com.inventory_managerment.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequest(
    @NotBlank(message = "ResfreshToken is required")
    String refreshToken
) {
    
}
