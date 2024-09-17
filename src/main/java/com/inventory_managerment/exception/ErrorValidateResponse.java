package com.inventory_managerment.exception;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorValidateResponse(
        Integer code,
        List<?> reason
) {
}
