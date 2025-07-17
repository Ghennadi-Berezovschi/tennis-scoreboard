package com.example.tennisscoreboard.dto;

/**
 * A simple DTO to represent error responses in JSON format.
 */
public class ErrorResponseDto {

    private int code;         // HTTP status code or custom error code
    private String message;   // Error message for the client

    /**
     * Default constructor (required for frameworks like Jackson).
     */
    public ErrorResponseDto() {
    }

    /**
     * Constructor with all fields.
     *
     * @param code    error or status code
     * @param message error message
     */
    public ErrorResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter for code
    public int getCode() {
        return code;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }
}
