package com.dawid.overtime.employee.exception;

public class UnauthorizedAccessAttemptException extends RuntimeException {
    public UnauthorizedAccessAttemptException(String message) {
        super(message);
    }

    public UnauthorizedAccessAttemptException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedAccessAttemptException(Throwable cause) {
        super(cause);
    }
}
