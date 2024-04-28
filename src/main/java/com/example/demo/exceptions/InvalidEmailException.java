package com.example.demo.exceptions;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException() {
        super("Email not found");
    }
}
