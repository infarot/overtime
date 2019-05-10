package com.dawid.overtime.security.handler;

import com.dawid.overtime.security.exception.UsernameAlreadyTakenException;
import com.dawid.overtime.security.response.SecurityExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SecurityExceptionResponse> handleException(UsernameAlreadyTakenException e) {
        SecurityExceptionResponse securityExceptionResponse =
                new SecurityExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(securityExceptionResponse, HttpStatus.CONFLICT);
    }

}
