package com.example.tennisscoreboard.exception;

/**
 * Thrown when page parameter is missing or not a valid number.
 */
public class InvalidPageParameterException extends RuntimeException {
    public InvalidPageParameterException(String message) {
        super(message);
    }
}
