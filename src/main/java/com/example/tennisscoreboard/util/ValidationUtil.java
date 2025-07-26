package com.example.tennisscoreboard.util;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.exception.InvalidPageParameterException;

import java.util.Optional;
import java.util.UUID;

public class ValidationUtil {

    public static void validateRequired(String param, String name) {
        if (param == null || param.isBlank()) {
            throw new InvalidInputException("Required parameter '" + name + "' is missing or blank.");
        }
    }

    public static void validateRequiredParams(String... params) {
        int index = 1;
        for (String param : params) {
            validateRequired(param, "param" + index++);
        }
    }

    public static UUID validateAndParseUUID(String uuidStr) {
        validateRequired(uuidStr, "uuid");
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("UUID format is invalid.");
        }
    }

    public static void validatePlayerNames(String player1Name, String player2Name) {
        if (isInvalidInput(player1Name, player2Name)) {
            throw new InvalidInputException("Invalid input: check names are not empty and not equal.");
        }
    }

    public static boolean isInvalidInput(String player1Name, String player2Name) {
        return player1Name == null
                || player2Name == null
                || player1Name.isBlank()
                || player2Name.isBlank()
                || player1Name.equals(player2Name);
    }

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

    public static Optional<String> validateFilterParam(String filterParam) {
        if (filterParam != null && !filterParam.isBlank()) {
            return Optional.of(filterParam.trim());
        } else {
            return Optional.empty();
        }
    }
}
