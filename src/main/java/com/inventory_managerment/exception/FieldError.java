package com.query_mysql.demo.exception;

import lombok.Builder;

@Builder
public record FieldError(
        String field,
        String detail
) {
}
