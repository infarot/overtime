package com.dawid.overtime.employee.exception;

public class EmployeeIdNotFoundException extends RuntimeException {

    public EmployeeIdNotFoundException(String message) {
        super(message);
    }

    public EmployeeIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeIdNotFoundException(Throwable cause) {
        super(cause);
    }
}
