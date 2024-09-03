package com.inventory_managerment.feature.payment.dto;
import java.math.BigDecimal;

public record PaymentUpdateRequest(
    Long orderId,
    String paymentMethod,
    BigDecimal amount
) {
    
}
