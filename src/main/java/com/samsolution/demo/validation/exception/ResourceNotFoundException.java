package com.samsolution.demo.validation.exception;

import com.samsolution.demo.validation.ErrorCode;

public class ResourceNotFoundException extends BaseValidationException {
    public ResourceNotFoundException(String message) {
        super(message);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }
}
