package com.samsolution.demo.validation;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ValidationErrorCode {
    INVALID_PARAM(HttpStatus.BAD_REQUEST, "Invalid param in request"),
    INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST, "Invalid Employee birthday param in request");

    private HttpStatus status;
    private String message;

    ValidationErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
