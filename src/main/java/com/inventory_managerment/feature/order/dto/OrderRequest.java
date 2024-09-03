package com.inventory_managerment.feature.order.dto;
import java.util.List;

import com.inventory_managerment.feature.product.dto.ProductUserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderRequest( 
    @NotNull(message="Customer Id required")
    Integer customerId,
    @NotNull(message="User Id required")
    Long userId,
    List<ProductUserRequest> products,
    String remark,
    String status
) {
}
