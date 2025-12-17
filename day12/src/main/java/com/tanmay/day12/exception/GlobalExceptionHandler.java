package com.tanmay.day12.exception;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(e ->
                errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<@NonNull ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<@NonNull ApiError> handleTaskNotFound(TaskNotFoundException ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
