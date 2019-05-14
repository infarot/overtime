package com.dawid.overtime.employee.exception;

public class UnathorizedDeleteAttemptException extends RuntimeException {

    public UnathorizedDeleteAttemptException(String message) {
        super(message);
    }

    public UnathorizedDeleteAttemptException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnathorizedDeleteAttemptException(Throwable cause) {
        super(cause);
    }
}
