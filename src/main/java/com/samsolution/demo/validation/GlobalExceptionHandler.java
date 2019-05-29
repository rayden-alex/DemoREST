package com.samsolution.demo.validation;

import com.samsolution.demo.controller.EmployeeController;
import com.samsolution.demo.validation.exception.BaseValidationException;
import com.samsolution.demo.validation.exception.BirthdayValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackageClasses = EmployeeController.class)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BirthdayValidationException.class)
    @ResponseBody
    public <T extends BirthdayValidationException> ResponseEntity<?> handleBirthdayValidationException(T ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getErrorCode(), ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(BaseValidationException.class)
    @ResponseBody
    public <T extends BaseValidationException> ResponseEntity<?> handleBaseValidationException(T ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getErrorCode(), ex.getErrorCode().getStatus());
    }


//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
