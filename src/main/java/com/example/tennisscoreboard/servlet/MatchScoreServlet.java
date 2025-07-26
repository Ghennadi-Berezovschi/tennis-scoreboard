package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.service.OngoingMatchesService;
import com.example.tennisscoreboard.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UUID matchUuid = ValidationUtil.validateAndParseUUID(request.getParameter("uuid"));
        MatchScore matchScore = ongoingMatchesService.getMatchScore(matchUuid);


        request.setAttribute("matchUuid", matchUuid.toString());
        request.setAttribute("match", matchScore);

        request.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(request, response);
    }
}
