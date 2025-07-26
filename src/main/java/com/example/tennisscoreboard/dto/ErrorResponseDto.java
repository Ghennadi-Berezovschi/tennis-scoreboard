package com.example.tennisscoreboard.dto;


public class ErrorResponseDto {

    private int code;
    private String message;


    public ErrorResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
