package com.tanmay.day12.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
