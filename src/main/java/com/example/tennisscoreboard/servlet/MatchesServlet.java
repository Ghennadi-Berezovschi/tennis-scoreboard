package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.dao.MatchDao;
import com.example.tennisscoreboard.model.Match;
import com.example.tennisscoreboard.util.ValidationUtils;
import com.example.tennisscoreboard.exception.InvalidPageParameterException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchDao matchDao = new MatchDao();
    private final int pageSize = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page;
        try {
            String pageParam = req.getParameter("page");
            page = ValidationUtils.validatePageParam(pageParam);
        } catch (InvalidPageParameterException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        String filter = req.getParameter("filter_by_player_name");
        String sanitizedFilter = ValidationUtils.sanitizeFilter(filter);

        List<Match> matches;
        long totalMatches;

        if (sanitizedFilter != null) {
            matches = matchDao.findMatchesByPlayerNamePaged(sanitizedFilter, page, pageSize);
            totalMatches = matchDao.countMatchesByPlayerName(sanitizedFilter);
            req.setAttribute("filter_by_player_name", sanitizedFilter);
        } else {
            matches = matchDao.findMatchesPaged(page, pageSize);
            totalMatches = matchDao.countAllMatches();
        }

        long totalPages = (long) Math.ceil((double) totalMatches / pageSize);

        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/WEB-INF/matches.jsp").forward(req, resp);
    }
}
