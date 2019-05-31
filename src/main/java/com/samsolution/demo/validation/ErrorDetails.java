package com.samsolution.demo.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String exceptionMessage;
    private String customMessage;
    private String details;
    private ErrorCode errorCode;
}