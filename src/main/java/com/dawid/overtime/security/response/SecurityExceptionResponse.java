package com.dawid.overtime.security.response;


public class SecurityExceptionResponse {
    private int status;
    private String message;
    private long timeStamp;

    public SecurityExceptionResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
