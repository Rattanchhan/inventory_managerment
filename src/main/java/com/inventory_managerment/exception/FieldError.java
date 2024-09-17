package com.inventory_managerment.exception;
import lombok.Builder;

@Builder
public record FieldError(
        String field,
        String detail
) {
}
