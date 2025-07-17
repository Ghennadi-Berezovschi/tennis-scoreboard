package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.service.MatchScoreCalculationService;
import com.example.tennisscoreboard.service.OngoingMatchesService;
import com.example.tennisscoreboard.util.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-point")
public class PointServlet extends HttpServlet {
    private static final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private static final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String matchIdParam = req.getParameter("uuid");
        String playerParam = req.getParameter("player");

        ValidationUtils.validateRequiredParams(matchIdParam, playerParam);

        UUID matchUuid = UUID.fromString(matchIdParam);
        MatchScore match = ongoingMatchesService.getMatchScoreOrThrow(matchUuid);

        switch (playerParam) {
            case "1" -> matchScoreCalculationService.player1WinsPoint(match);
            case "2" -> matchScoreCalculationService.player2WinsPoint(match);
            default -> throw new InvalidInputException("Invalid player parameter: must be 1 or 2.");
        }

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchUuid);
    }

}



