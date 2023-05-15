package com.service.freeblog_admin.web.error.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
    private final String message;
    private final int statusCode;
}
