package com.example.tennisscoreboard.model;

import java.util.Optional;

public class MatchScore {

    private String player1Name;
    private String player2Name;

    private int player1Points;
    private int player2Points;

    private int player1Games;
    private int player2Games;

    private int player1Sets;
    private int player2Sets;

    private boolean isFinished;




    public MatchScore(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Points = 0;
        this.player2Points = 0;
        this.player1Games = 0;
        this.player2Games = 0;
        this.player1Sets = 0;
        this.player2Sets = 0;
        this.isFinished = false;
    }



    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public void setPlayer1Points(int player1Points) {
        this.player1Points = player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    public void setPlayer2Points(int player2Points) {
        this.player2Points = player2Points;
    }

    public int getPlayer1Games() {
        return player1Games;
    }

    public void setPlayer1Games(int player1Games) {
        this.player1Games = player1Games;
    }

    public int getPlayer2Games() {
        return player2Games;
    }

    public void setPlayer2Games(int player2Games) {
        this.player2Games = player2Games;
    }

    public int getPlayer1Sets() {
        return player1Sets;
    }

    public void setPlayer1Sets(int player1Sets) {
        this.player1Sets = player1Sets;
    }

    public int getPlayer2Sets() {
        return player2Sets;
    }

    public void setPlayer2Sets(int player2Sets) {
        this.player2Sets = player2Sets;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }


    public Optional<String> getWinnerName() {
        if (!isFinished) return Optional.empty();

        if (player1Sets > player2Sets) {
            return Optional.of(player1Name);
        } else if (player2Sets > player1Sets) {
            return Optional.of(player2Name);
        } else {
            return Optional.empty();
        }
    }


}

