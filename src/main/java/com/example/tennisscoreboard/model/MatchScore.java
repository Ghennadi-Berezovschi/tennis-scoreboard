package com.example.tennisscoreboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class MatchScore {

    private String firstPlayerName;
    private String secondPlayerName;

    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private int firstPlayerGames;
    private int secondPlayerGames;

    private int firstPlayerSets;
    private int secondPlayerSets;

    private boolean isFinished;


    public MatchScore(String player1Name, String player2Name) {
        this.firstPlayerName = player1Name;
        this.secondPlayerName = player2Name;
        this.firstPlayerPoints = 0;
        this.secondPlayerPoints = 0;
        this.firstPlayerGames = 0;
        this.secondPlayerGames = 0;
        this.firstPlayerSets = 0;
        this.secondPlayerSets = 0;
        this.isFinished = false;
    }

    public Optional<String> getWinnerName() {
        if (!isFinished) return Optional.empty();

        if (firstPlayerSets > secondPlayerSets) {
            return Optional.of(firstPlayerName);
        } else if (secondPlayerSets > firstPlayerSets) {
            return Optional.of(secondPlayerName);
        } else {
            return Optional.empty();
        }
    }
}
