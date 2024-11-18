package com.capitravel.Capitravel.exception;

public class DuplicatedResourceException extends RuntimeException{

    public DuplicatedResourceException(String message) {
        super(message);
    }

    public DuplicatedResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
