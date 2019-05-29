package com.samsolution.demo.validation.exception;

import com.samsolution.demo.validation.ValidationErrorCode;
import lombok.Getter;

@Getter
public class BaseValidationException extends RuntimeException {
    private ValidationErrorCode errorCode = ValidationErrorCode.INVALID_PARAM;
}