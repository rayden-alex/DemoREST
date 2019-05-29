package com.samsolution.demo.validation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String exceptionMessage;
    private String customMessage;
    private String details;
    private ErrorCode errorCode;
}