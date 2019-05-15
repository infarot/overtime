package com.dawid.overtime.employee.handler;

import com.dawid.overtime.employee.exception.EmployeeIdNotFoundException;
import com.dawid.overtime.employee.exception.UnathorizedDeleteAttemptException;
import com.dawid.overtime.response.OvertimeExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<OvertimeExceptionResponse> handleException(EmployeeIdNotFoundException e) {
        OvertimeExceptionResponse response = new OvertimeExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<OvertimeExceptionResponse> handleException(UnathorizedDeleteAttemptException e) {
        OvertimeExceptionResponse response = new OvertimeExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<OvertimeExceptionResponse> handleException(NumberFormatException e){
        OvertimeExceptionResponse response = new OvertimeExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
