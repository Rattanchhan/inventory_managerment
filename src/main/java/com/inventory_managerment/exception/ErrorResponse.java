package com.query_mysql.demo.exception;

import lombok.Builder;

@Builder
public record ErrorResponse (
        Integer code,
        String reason
){
}
