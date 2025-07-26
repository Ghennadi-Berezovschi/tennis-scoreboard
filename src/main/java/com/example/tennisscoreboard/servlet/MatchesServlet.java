package com.example.tennisscoreboard.servlet;

import com.example.tennisscoreboard.dto.MatchResponseDto;
import com.example.tennisscoreboard.service.MatchService;
import com.example.tennisscoreboard.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchService matchService = new MatchService();
    private final int pageSize = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = ValidationUtil.validatePageParam(req.getParameter("page"));
        Optional<String> optionalFilter = ValidationUtil.validateFilterParam(
                req.getParameter("filter_by_player_name")
        );
        String filter = optionalFilter.orElse(null);

        List<MatchResponseDto> dtoList = matchService.getMatchDtos(page, pageSize, filter);
        long totalMatches = matchService.getTotalMatches(filter);
        long totalPages = (long) Math.ceil((double) totalMatches / pageSize);

        req.setAttribute("matches", dtoList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        optionalFilter.ifPresent(f -> req.setAttribute("filter_by_player_name", f));

        req.getRequestDispatcher("/WEB-INF/matches.jsp").forward(req, resp);
    }
}
