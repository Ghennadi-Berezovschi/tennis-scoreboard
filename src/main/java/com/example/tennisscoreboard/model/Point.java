package com.example.tennisscoreboard.model;

public enum Point {
    LOVE("0"),
    FIFTEEN ("15"),
    THIRTY("30"),
    FORTY("40");


    private final String display;

    Point(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
    public static Point fromInt(int value) {
        return switch (value){
            case 0 -> LOVE;
            case 1 -> FIFTEEN;
            case 2 -> THIRTY;
            case 3 -> FORTY;
            default -> throw new IllegalArgumentException("Invalid point value: " + value);
        };
    }
}
