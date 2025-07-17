package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.model.Match;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.model.Player;
import com.example.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.example.tennisscoreboard.service.OngoingMatchesService;
import com.example.tennisscoreboard.service.PlayerService;
import com.example.tennisscoreboard.util.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/end-match")
public class EndMatchServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final FinishedMatchesPersistenceService finishedMatchesService = new FinishedMatchesPersistenceService();
    private final PlayerService playerService = new PlayerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Validate UUID
        UUID matchUuid = ValidationUtils.validateAndParseUUID(request.getParameter("uuid"));

        // 2. Get MatchScore from memory
        MatchScore matchScore = ongoingMatchesService.getMatchScoreOrThrow(matchUuid);

        // 3. Check if match is finished
        if (!matchScore.isFinished()) {
            throw new InvalidInputException("Match is not finished yet.");
        }

        // 4. Get winner name
        Optional<String> winnerNameOpt = matchScore.getWinnerName();
        if (winnerNameOpt.isEmpty()) {
            throw new InvalidInputException("Cannot determine winner.");
        }
        String winnerName = winnerNameOpt.get();

        // 5. Get or create players
        Player player1 = playerService.getOrCreatePlayer(matchScore.getPlayer1Name());
        Player player2 = playerService.getOrCreatePlayer(matchScore.getPlayer2Name());
        Player winner = winnerName.equals(player1.getName()) ? player1 : player2;

        // 6. Save finished match to DB
        Match finishedMatch = new Match(player1, player2, winner);
        finishedMatchesService.saveFinishedMatch(finishedMatch);

        // 7. Remove match from memory
        ongoingMatchesService.removeMatch(matchUuid);

        // 8. Redirect to homepage
        response.sendRedirect(request.getContextPath() + "/matches");
    }
}
