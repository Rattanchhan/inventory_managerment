package com.inventory_managerment.feature.user.dto;
import com.inventory_managerment.feature.role.dto.RoleResponse;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record UserResponse(
        String name,
        String userName,
        String position,
        String remark,
        LocalDate date_of_birth,
        RoleResponse role
) {
}
