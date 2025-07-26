package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.service.MatchScoreCalculationService;
import com.example.tennisscoreboard.service.OngoingMatchesService;
import com.example.tennisscoreboard.util.ValidationUtil;
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
            throws  IOException {

        String matchIdParam = req.getParameter("uuid");
        String playerParam = req.getParameter("player");

        ValidationUtil.validateRequired(matchIdParam, "uuid");
        ValidationUtil.validateRequired(playerParam, "player");

        UUID matchUuid = ValidationUtil.validateAndParseUUID(matchIdParam);
        MatchScore match = ongoingMatchesService.getMatchScore(matchUuid);


        matchScoreCalculationService.processPlayerPoint(match, playerParam);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchUuid);

    }
}
