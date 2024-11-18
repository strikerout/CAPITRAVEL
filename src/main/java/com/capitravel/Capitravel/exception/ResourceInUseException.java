package com.capitravel.Capitravel.exception;

public class ResourceInUseException extends RuntimeException{

    public ResourceInUseException(String message) {
        super(message);
    }

    public ResourceInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
