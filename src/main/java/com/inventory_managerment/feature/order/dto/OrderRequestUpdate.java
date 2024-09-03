package com.inventory_managerment.feature.order.dto;
import lombok.Builder;

@Builder
public record OrderRequestUpdate(
    String remark,
    String status
){
    
}
