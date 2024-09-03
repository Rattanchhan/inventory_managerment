package com.inventory_managerment.feature.order.dto;
import com.inventory_managerment.feature.orderItem.dto.OrderItemsResponse;
import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import java.util.Date;
import java.util.List;

@Builder
public record OrderResponse(
    Long staffId,
    String staffName,
    String position,
    Integer customerId,
    String customerName,
    String phone,
    String point,
    String status,
    String remark,
    List<OrderItemsResponse> orderItems,
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt
) {
}
