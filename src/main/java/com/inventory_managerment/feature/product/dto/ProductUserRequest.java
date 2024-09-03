package com.inventory_managerment.feature.product.dto;
import lombok.Builder;
import java.math.*;

@Builder
public record ProductUserRequest(
    Long productId,
    Integer qty,
    BigDecimal unitPrice ,
    BigDecimal discountAmount

){}
