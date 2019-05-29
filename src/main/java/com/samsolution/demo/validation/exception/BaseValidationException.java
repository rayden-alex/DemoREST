package com.samsolution.demo.validation.exception;

import com.samsolution.demo.validation.ErrorCode;
import lombok.Getter;

@Getter
public class BaseValidationException extends RuntimeException {
    private ErrorCode errorCode;

    BaseValidationException(String message) {
        super(message);
        setErrorCode(ErrorCode.ABSTRACT_INVALID_PARAM);
    }

    void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}