package com.samsolution.demo.validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String exceptionMessage;
    private String customMessage;
    private String details;
    private ErrorCode errorCode;
}