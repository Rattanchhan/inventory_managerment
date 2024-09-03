package com.inventory_managerment.feature.payment.dto;
import java.math.BigDecimal;

public record PaymentResponse(
    Long orderId,
    BigDecimal totalAmount,
    BigDecimal totalDiscount,
    BigDecimal paymentAmount,
    String paymentMethod,
    BigDecimal paidAmount,
    BigDecimal ownAmount,
    String status
) {
    
}
