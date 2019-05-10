package com.dawid.overtime.security.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }

    public UsernameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}
