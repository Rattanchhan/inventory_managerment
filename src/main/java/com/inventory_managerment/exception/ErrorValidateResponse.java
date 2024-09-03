package com.query_mysql.demo.exception;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorValidateResponse(
        Integer code,
        List<?> reason
) {
}
