package com.samsolution.demo.validation.exception;

import com.samsolution.demo.validation.ErrorCode;

public class EntityNotFoundException extends BaseValidationException {
    public EntityNotFoundException(String message) {
        super(message);
        setErrorCode(ErrorCode.ENTITY_NOT_FOUND);
    }
}
