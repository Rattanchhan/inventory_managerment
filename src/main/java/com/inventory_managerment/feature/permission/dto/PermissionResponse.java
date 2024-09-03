package com.inventory_managerment.feature.permission.dto;

public record PermissionResponse (
    Long id,
    String code,
    String name,
    String module
){
    
}
