package com.inventory_managerment.feature.auth.dto;

public record ChangePasswordRequest(
    String oldPassword,
    String newPassword,
    String confirmPassword
) {
    
}
