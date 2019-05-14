package com.dawid.overtime.response;


public class OvertimeExceptionResponse {
    private int status;
    private String message;
    private long timeStamp;

    public OvertimeExceptionResponse(int status, String message, long timeStamp) {
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
