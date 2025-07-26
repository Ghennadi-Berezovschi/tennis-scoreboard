package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.service.MatchService;
import com.example.tennisscoreboard.util.ValidationUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/end-match")
public class EndMatchServlet extends HttpServlet {

    private final MatchService matchService = new MatchService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UUID matchUuid = ValidationUtil.validateAndParseUUID(request.getParameter("uuid"));

        matchService.endAndSaveMatch(matchUuid);

        response.sendRedirect(request.getContextPath() + "/matches");

    }
}


