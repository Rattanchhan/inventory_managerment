package com.inventory_managerment.feature.user.dto;
import com.inventory_managerment.feature.role.dto.RoleResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record UserRequest(
        @NotBlank
        String name,
        @NotBlank
        String userName,
        @NotBlank(message="Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
        String password,
        @NotBlank
        String position,
        @NotNull
        @Positive
        BigDecimal salary,
        String remark,
        LocalDate date_of_birth,
        Long roleId,
        Long userId
) {
}
