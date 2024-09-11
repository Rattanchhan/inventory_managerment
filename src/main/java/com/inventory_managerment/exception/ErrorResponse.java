package com.inventory_managerment.exception;

import lombok.Builder;

@Builder
public record ErrorResponse (
        Integer code,
        String reason
){
}
