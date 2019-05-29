package com.samsolution.demo.validation.exception;

import com.samsolution.demo.validation.ErrorCode;

public class BirthdayValidationException extends BaseValidationException {

    public BirthdayValidationException(String message) {
        super(message);
        setErrorCode(ErrorCode.EMPLOYEE_INVALID_BIRTHDAY);
    }
}
