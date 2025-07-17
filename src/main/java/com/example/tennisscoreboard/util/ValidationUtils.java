package com.example.tennisscoreboard.util;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.exception.InvalidPageParameterException;

import java.util.UUID;

public class ValidationUtils {

    public static boolean isInvalidInput(String player1Name, String player2Name) {
        return player1Name == null
                || player2Name == null
                || player1Name.isBlank()
                || player2Name.isBlank()
                || player1Name.equals(player2Name);
    }

    public static UUID validateAndParseUUID(String uuidStr) {
        if (uuidStr == null || uuidStr.isBlank()) {
            throw new InvalidInputException("UUID is missing or blank.");
        }
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("UUID format is invalid.");
        }
    }

    public static void validateRequiredParams(String... params) {
        for (String param : params) {
            if (param == null || param.isBlank()) {
                throw new InvalidInputException("Missing or blank required parameter.");
            }
        }
    }

    //  New: validate page parameter
    public static int validatePageParam(String pageParam) {
        if (pageParam == null || pageParam.isBlank()) {
            return 1;
        }

        try {
            int page = Integer.parseInt(pageParam);
            if (page < 1) {
                throw new InvalidPageParameterException("Page number must be at least 1.");
            }
            return page;
        } catch (NumberFormatException e) {
            throw new InvalidPageParameterException("Page parameter must be a valid number.");
        }
    }

    //  New: sanitize filter
    public static String sanitizeFilter(String filter) {
        return (filter != null && !filter.isBlank()) ? filter.trim() : null;
    }
}
