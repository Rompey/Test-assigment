package com.example.demo.exceptions.handler;

import com.example.demo.exceptions.InvalidAgeException;
import com.example.demo.exceptions.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Error> handleEmailNotValidException(InvalidEmailException e) {
        return handleException(BAD_REQUEST, e);
    }

    @ExceptionHandler(InvalidAgeException.class)
    public ResponseEntity<Error> handleAgeNotValidException(InvalidAgeException e) {
        return handleException(BAD_REQUEST, e);
    }

    private ResponseEntity<Error> handleException(HttpStatus status, RuntimeException runtimeException) {
        return ResponseEntity.status(status).body(showErrorMessage(status, runtimeException));
    }

    public Error showErrorMessage(HttpStatus status, Exception exception) {
        return new Error(status, exception.getMessage());
    }

    private record Error(HttpStatus status, String message) {

    }
}
