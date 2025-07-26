package com.example.tennisscoreboard.servlet;


import com.example.tennisscoreboard.service.OngoingMatchesService;
import com.example.tennisscoreboard.service.PlayerPersistenceService;
import com.example.tennisscoreboard.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final PlayerPersistenceService playerPersistenceService = new PlayerPersistenceService();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");


        ValidationUtil.validatePlayerNames(player1Name, player2Name);
        UUID matchUuid = ongoingMatchesService.createMatch(player1Name, player2Name);


        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchUuid.toString());
    }
}
