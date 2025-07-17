package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.model.MatchScore;

/**
 * Service for calculating match score logic in a tennis match.
 */
public class MatchScoreCalculationService {

    /**
     * Handles logic when Player 1 wins a point.
     *
     * @param match the current match score state
     */
    public void player1WinsPoint(MatchScore match) {
        calculatePoint(match, true);
    }

    /**
     * Handles logic when Player 2 wins a point.
     *
     * @param match the current match score state
     */
    public void player2WinsPoint(MatchScore match) {
        calculatePoint(match, false);
    }

    /**
     * Common logic for updating points when a player wins a point.
     *
     * @param match     the current match
     * @param isPlayer1 true if Player 1 scored the point, false for Player 2
     */
    private void calculatePoint(MatchScore match, boolean isPlayer1) {
        if (match.isFinished()) {
            return;
        }

        int p1 = match.getPlayer1Points();
        int p2 = match.getPlayer2Points();

        if (isPlayer1) p1++;
        else p2++;

        // Game win condition (40 or more points)
        if (p1 >= 4 || p2 >= 4) {
            int diff = p1 - p2;

            if (diff >= 2) {
                // Player 1 wins the game
                match.setPlayer1Games(match.getPlayer1Games() + 1);
                p1 = 0;
                p2 = 0;
                checkSetWin(match);
            } else if (diff <= -2) {
                // Player 2 wins the game
                match.setPlayer2Games(match.getPlayer2Games() + 1);
                p1 = 0;
                p2 = 0;
                checkSetWin(match);
            }
        }

        match.setPlayer1Points(p1);
        match.setPlayer2Points(p2);
    }

    /**
     * Checks whether a player has won a set or the entire match.
     *
     * @param match the current match
     */
    private void checkSetWin(MatchScore match) {
        int p1Games = match.getPlayer1Games();
        int p2Games = match.getPlayer2Games();

        // Set win condition: 6+ games with a 2-game lead
        if (p1Games >= 6 && p1Games - p2Games >= 2) {
            match.setPlayer1Sets(match.getPlayer1Sets() + 1);
            match.setPlayer1Games(0);
            match.setPlayer2Games(0);
        } else if (p2Games >= 6 && p2Games - p1Games >= 2) {
            match.setPlayer2Sets(match.getPlayer2Sets() + 1);
            match.setPlayer1Games(0);
            match.setPlayer2Games(0);
        }

        // Match win condition: best of 3 sets
        if (match.getPlayer1Sets() == 2 || match.getPlayer2Sets() == 2) {
            match.setFinished(true);
        }
    }

    /**
     * Converts raw point values to display-friendly tennis scores.
     *
     * @param points         current player's points
     * @param opponentPoints opponent's points
     * @return formatted string: 0, 15, 30, 40, Deuce, Advantage, etc.
     */
    public String getPointsDisplay(int points, int opponentPoints) {

        // Normal scoring (0–40)
        if (points <= 3 && opponentPoints <= 3 && !(points == 3 && opponentPoints == 3)) {
            switch (points) {
                case 0: return "0";
                case 1: return "15";
                case 2: return "30";
                case 3: return "40";
            }
        }

        // Deuce and Advantage logic
        if (points >= 3 && opponentPoints >= 3) {
            if (points == opponentPoints) {
                return "Deuce";
            } else if (points - opponentPoints == 1) {
                return "Advantage";
            } else if (points - opponentPoints == -1) {
                return ""; // Opponent has Advantage
            } else if (points - opponentPoints >= 2) {
                return "Game";
            }
        }

        return "";
    }
}
