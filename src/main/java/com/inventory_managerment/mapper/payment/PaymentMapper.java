package com.inventory_managerment.mapper.payment;

import com.inventory_managerment.domain.Payment;
import com.inventory_managerment.feature.payment.dto.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    
    @Mapping(target = "orderId",source = "order.id")
    PaymentResponse fromPayment(Payment payment);
}
