package com.inventory_managerment.feature.user.dto;
import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record UserUpdateRequest(
        String name,
        String userName,
        String position,
        BigDecimal salary,
        String remark,
        String status
) {
}
