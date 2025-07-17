package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.dao.PlayerDao;
import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.model.MatchScore;
import com.example.tennisscoreboard.model.Player;
import com.example.tennisscoreboard.service.OngoingMatchesService;

import com.example.tennisscoreboard.service.PlayerService;
import com.example.tennisscoreboard.util.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final PlayerService playerService = new PlayerService();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        if (ValidationUtils.isInvalidInput(player1Name, player2Name)) {
            throw new InvalidInputException("Invalid input: check names are not empty and not equal.");
        }


        Player player1 = playerService.getOrCreatePlayer(player1Name);
        Player player2 = playerService.getOrCreatePlayer(player2Name);

        MatchScore matchScore = new MatchScore(player1.getName(), player2.getName());
        //create uuid of match
        UUID matchId = ongoingMatchesService.createMatch(matchScore);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
    }


}
