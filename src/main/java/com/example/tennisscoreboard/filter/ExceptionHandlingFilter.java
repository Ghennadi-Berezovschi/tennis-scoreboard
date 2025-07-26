package com.example.tennisscoreboard.filter;

import com.example.tennisscoreboard.dto.ErrorResponseDto;
import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.exception.InvalidPageParameterException;
import com.example.tennisscoreboard.exception.MatchStateException;
import com.example.tennisscoreboard.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {

        HttpServletResponse res = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (InvalidInputException e) {
            writeErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (NotFoundException e) {
            writeErrorResponse(res, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (MatchStateException e) {
            writeErrorResponse(res, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (InvalidPageParameterException e) {
            writeErrorResponse(res, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }

        catch (Exception e) {
            writeErrorResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }

    private void writeErrorResponse(HttpServletResponse res, int statusCode, String message) throws IOException {
        res.setStatus(statusCode);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        ErrorResponseDto dto = new ErrorResponseDto(statusCode, message);
        String json = objectMapper.writeValueAsString(dto);
        res.getWriter().write(json);
    }
}

