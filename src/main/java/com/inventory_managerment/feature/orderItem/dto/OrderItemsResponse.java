package com.inventory_managerment.feature.orderItem.dto;
import java.math.*;

public record OrderItemsResponse(
    BigDecimal unitPrice,
    Integer qty,
    BigDecimal discountAmount,
    Long productId
) {
}
