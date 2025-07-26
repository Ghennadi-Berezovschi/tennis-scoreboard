package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.model.PlayerScorer;
import com.example.tennisscoreboard.model.Point;

public class MatchScoreCalculationService {

    public static final int POINTS_TO_GAME = 4;
    public static final int GAMES_TO_SET = 6;
    public static final int SET_WIN_MARGIN = 2;
    public static final int SETS_TO_MATCH = 2;

    public void recordPoint(MatchScore matchScore, PlayerScorer playerScorer) {
        if (matchScore.isFinished()) {
            return;
        }

        int p1 = matchScore.getFirstPlayerPoints();
        int p2 = matchScore.getSecondPlayerPoints();

        if (playerScorer == PlayerScorer.PLAYER_1) {
            p1++;
        } else {
            p2++;
        }

        if (p1 >= POINTS_TO_GAME || p2 >= POINTS_TO_GAME) {
            int difference = p1 - p2;

            if (difference >= SET_WIN_MARGIN) {
                matchScore.setFirstPlayerGames(matchScore.getFirstPlayerGames() + 1);
                resetPoints(matchScore);
                checkSetWin(matchScore);
                return;
            } else if (difference <= -SET_WIN_MARGIN) {
                matchScore.setSecondPlayerGames(matchScore.getSecondPlayerGames() + 1);
                resetPoints(matchScore);
                checkSetWin(matchScore);
                return;
            }
        }

        matchScore.setFirstPlayerPoints(p1);
        matchScore.setSecondPlayerPoints(p2);
    }

    private void resetPoints(MatchScore matchScore) {
        matchScore.setFirstPlayerPoints(0);
        matchScore.setSecondPlayerPoints(0);
    }

    private void checkSetWin(MatchScore match) {
        int p1Games = match.getFirstPlayerGames();
        int p2Games = match.getSecondPlayerGames();

        if (p1Games >= GAMES_TO_SET && p1Games - p2Games >= SET_WIN_MARGIN) {
            match.setFirstPlayerSets(match.getFirstPlayerSets() + 1);
            match.setFirstPlayerGames(0);
            match.setSecondPlayerGames(0);
        } else if (p2Games >= GAMES_TO_SET && p2Games - p1Games >= SET_WIN_MARGIN) {
            match.setSecondPlayerSets(match.getSecondPlayerSets() + 1);
            match.setFirstPlayerGames(0);
            match.setSecondPlayerGames(0);
        }

        if (match.getFirstPlayerSets() == SETS_TO_MATCH || match.getSecondPlayerSets() == SETS_TO_MATCH) {
            match.setFinished(true);
        }
    }

    public String getPointsDisplay(int points, int opponentPoints) {
        if (points <= 3 && opponentPoints <= 3 && !(points == 3 && opponentPoints == 3)) {
            return Point.fromInt(points).getDisplay();
        }

        if (points >= 3 && opponentPoints >= 3) {
            if (points == opponentPoints) {
                return "Deuce";
            } else if (points - opponentPoints == 1) {
                return "Advantage";
            } else if (points - opponentPoints == -1) {
                return "";
            } else if (points - opponentPoints >= SET_WIN_MARGIN) {
                return "Game";
            }
        }

        return "";
    }

    public void processPlayerPoint(MatchScore match, String playerParam) {
        switch (playerParam) {
            case "1" -> recordPoint(match, PlayerScorer.PLAYER_1);
            case "2" -> recordPoint(match, PlayerScorer.PLAYER_2);
            default -> throw new InvalidInputException("Invalid player parameter: must be 1 or 2.");
        }
    }
}
