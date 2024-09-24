package com.inventory_managerment.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
    
    String tokenType,
    String accessToken,
    String refeshToken
){
    
}
