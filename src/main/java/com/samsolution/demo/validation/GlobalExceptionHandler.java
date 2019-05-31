package com.samsolution.demo.validation;

import com.samsolution.demo.validation.exception.BaseValidationException;
import com.samsolution.demo.validation.exception.BirthdayValidationException;
import com.samsolution.demo.validation.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice//(basePackageClasses = EmployeeController.class)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T extends ResourceNotFoundException> ErrorDetails handleResourceNotFoundException(T ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .exceptionMessage(ex.getMessage())
                .details(request.getDescription(false))
                .customMessage("Resource Not Found")
                .errorCode(ex.getErrorCode())
                .build();

        return errorDetails;
    }

    @ExceptionHandler(BirthdayValidationException.class)
    @ResponseBody
    public <T extends BirthdayValidationException> ResponseEntity<?> handleBirthdayValidationException(T ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .exceptionMessage(ex.getMessage())
                .details(request.getDescription(false))
                .customMessage("Invalid Employee birthday param")
                .errorCode(ex.getErrorCode())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseValidationException.class)
    @ResponseBody
    public <T extends BaseValidationException> ResponseEntity<?> handleBaseValidationException(T ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .exceptionMessage(ex.getMessage())
                .details(request.getDescription(false))
                .customMessage("Invalid request param")
                .errorCode(ex.getErrorCode())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public <T extends Exception> ResponseEntity<?> handleUnexpectedErrorException(T ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .exceptionMessage(ex.getMessage())
                .details(request.getDescription(false))
                .customMessage("Unexpected Error")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .exceptionMessage(ex.getMessage())
                .details(request.getDescription(false))
                .customMessage("Method Argument Not Valid")
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }
}
