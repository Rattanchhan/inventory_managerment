package com.inventory_managerment.feature.role.dto;
import com.inventory_managerment.feature.permission.dto.PermissionResponse;

import java.util.List;

public record RoleResponse(
    Long id,
    String code,
    String name,
    List<PermissionResponse> rolePermissions
) {
    
}
