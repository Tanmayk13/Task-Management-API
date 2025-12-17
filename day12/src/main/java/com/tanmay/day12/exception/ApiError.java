package com.tanmay.day12.exception;

public class ApiError {
    private final String message;
    private final int status;

    public String getMessage() {
        return message;
    }

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
