package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.model.PlayerScorer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreCalculationServiceTest {

    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    /**
     * Player 1 wins a game when the score is 40-0
     */
    @Test
    void testPlayer1WinsGameAfter40_0() {
        MatchScore match = new MatchScore("Player1", "Player2");
        match.setFirstPlayerPoints(3); // 40
        match.setSecondPlayerPoints(0);

        matchScoreCalculationService.recordPoint(match, PlayerScorer.PLAYER_1);

        assertEquals(0, match.getFirstPlayerPoints(), "Player1 points should reset after winning game");
        assertEquals(0, match.getSecondPlayerPoints(), "Player2 points should reset after losing game");
        assertEquals(1, match.getFirstPlayerGames(), "Player1 should have +1 game");
    }

    /**
     * Transition from Deuce to Advantage to Game for Player 1
     */
    @Test
    void testDeuceToAdvantageToWin() {
        MatchScore match = new MatchScore("Player1", "Player2");
        match.setFirstPlayerPoints(3); // 40
        match.setSecondPlayerPoints(3); // 40

        // Player 1 wins a point -> Advantage
        matchScoreCalculationService.recordPoint(match, PlayerScorer.PLAYER_1);
        assertEquals("Advantage", matchScoreCalculationService.getPointsDisplay(match.getFirstPlayerPoints(), match.getSecondPlayerPoints()),
                "Player1 should have Advantage");

        // Player 1 wins another point -> Game
        matchScoreCalculationService.recordPoint(match, PlayerScorer.PLAYER_1);
        assertEquals(0, match.getFirstPlayerPoints(), "Player1 points should reset after winning game");
        assertEquals(1, match.getFirstPlayerGames(), "Player1 should have +1 game after winning from Advantage");
    }

    /**
     * Behavior when both players reach 6 games (tie-break not implemented, should behave normally)
     */
    @Test
    void testNormalGameAt6_6() {
        MatchScore match = new MatchScore("Player1", "Player2");
        match.setFirstPlayerGames(6);
        match.setSecondPlayerGames(6);

        match.setFirstPlayerPoints(3);
        match.setSecondPlayerPoints(3);

        matchScoreCalculationService.recordPoint(match, PlayerScorer.PLAYER_1); // Advantage
        matchScoreCalculationService.recordPoint(match, PlayerScorer.PLAYER_1); // Game

        assertEquals(7, match.getFirstPlayerGames(), "Player1 should have 7 games");
        assertEquals(0, match.getFirstPlayerPoints(), "Points should reset after game");
    }
}
