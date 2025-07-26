package com.example.tennisscoreboard.dto;

public class MatchResponseDto {

    private String playerFirstName;
    private String playerSecondName;
    private String playerWinner;

    public MatchResponseDto() {
    }

    public String getPlayerFirstName() {
        return playerFirstName;
    }

    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
    }

    public String getPlayerSecondName() {
        return playerSecondName;
    }

    public void setPlayerSecondName(String playerSecondName) {
        this.playerSecondName = playerSecondName;
    }

    public String getPlayerWinner() {
        return playerWinner;
    }

    public void setPlayerWinner(String playerWinner) {
        this.playerWinner = playerWinner;
    }
}

