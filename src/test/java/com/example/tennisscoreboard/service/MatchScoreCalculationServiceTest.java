package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.model.MatchScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreCalculationServiceTest {

    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    /**
     * Test 1:
     * Player 1 wins a game when the score is 40-0
     */
    @Test
    void testPlayer1WinsGameAfter40_0() {
        MatchScore match = new MatchScore("Player1", "Player2");
        match.setPlayer1Points(3); // 40
        match.setPlayer2Points(0);

        matchScoreCalculationService.player1WinsPoint(match);

        assertEquals(0, match.getPlayer1Points(), "Player1 points should reset after winning game");
        assertEquals(0, match.getPlayer2Points(), "Player2 points should reset after losing game");
        assertEquals(1, match.getPlayer1Games(), "Player1 should have +1 game");
    }

    /**
     * Test 2:
     * Transition from Deuce ➔ Advantage ➔ Game for Player 1
     */
    @Test
    void testDeuceToAdvantageToWin() {
        MatchScore match = new MatchScore("Player1", "Player2");
        match.setPlayer1Points(3); // 40
        match.setPlayer2Points(3); // 40

        // Player 1 wins a point ➔ Advantage
        matchScoreCalculationService.player1WinsPoint(match);
        assertEquals("Advantage", matchScoreCalculationService.getPointsDisplay(match.getPlayer1Points(), match.getPlayer2Points()),
                "Player1 should have Advantage");

        // Player 1 wins another point ➔ Game
        matchScoreCalculationService.player1WinsPoint(match);
        assertEquals(0, match.getPlayer1Points(), "Player1 points should reset after winning game");
        assertEquals(1, match.getPlayer1Games(), "Player1 should have +1 game after winning from Advantage");
    }

    /**
     * Test 3:
     * Tie-break behavior when both players reach 6 games
     * (Only valid if tie-break logic is implemented)
     */
    @Test
    void testTieBreakAt6_6() {
        MatchScore match = new MatchScore("Player1", "Player2");
        match.setPlayer1Games(6);
        match.setPlayer2Games(6);

        // If tie-break logic is supported, Player 1 should start gaining tie-break points
        matchScoreCalculationService.player1WinsPoint(match);

        // Expect Player1 to have 1 point if tie-break is triggered
        assertEquals(1, match.getPlayer1Points(), "Player1 should have 1 point in tie-break");
    }
}
