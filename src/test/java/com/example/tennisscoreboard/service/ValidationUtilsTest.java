package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.exception.InvalidPageParameterException;
import com.example.tennisscoreboard.util.ValidationUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void isInvalidInput_shouldReturnFalse_whenValidNames() {
        assertFalse(ValidationUtils.isInvalidInput("Alice", "Bob"));
    }

    @Test
    void isInvalidInput_shouldReturnTrue_whenNamesAreNullOrBlankOrSame() {
        assertTrue(ValidationUtils.isInvalidInput(null, "Bob"));
        assertTrue(ValidationUtils.isInvalidInput("Alice", null));
        assertTrue(ValidationUtils.isInvalidInput("  ", "Bob"));
        assertTrue(ValidationUtils.isInvalidInput("Alice", "  "));
        assertTrue(ValidationUtils.isInvalidInput("Same", "Same"));
    }

    @Test
    void validateAndParseUUID_shouldReturnUUID_whenValid() {
        String uuidStr = UUID.randomUUID().toString();
        UUID parsed = ValidationUtils.validateAndParseUUID(uuidStr);
        assertEquals(uuidStr, parsed.toString());
    }

    @Test
    void validateAndParseUUID_shouldThrow_whenInvalid() {
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateAndParseUUID(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateAndParseUUID(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateAndParseUUID("invalid-uuid"));
    }

    @Test
    void validateRequiredParams_shouldPass_whenAllValid() {
        assertDoesNotThrow(() -> ValidationUtils.validateRequiredParams("one", "two"));
    }

    @Test
    void validateRequiredParams_shouldThrow_whenAnyMissingOrBlank() {
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateRequiredParams("ok", null));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateRequiredParams("", "ok"));
        assertThrows(InvalidInputException.class, () -> ValidationUtils.validateRequiredParams(" "));
    }

    @Test
    void validatePageParam_shouldReturnParsedValue_whenValid() {
        assertEquals(3, ValidationUtils.validatePageParam("3"));
    }

    @Test
    void validatePageParam_shouldReturn1_whenBlankOrNull() {
        assertEquals(1, ValidationUtils.validatePageParam(null));
        assertEquals(1, ValidationUtils.validatePageParam(" "));
    }

    @Test
    void validatePageParam_shouldThrow_whenNegativeOrNonNumber() {
        assertThrows(InvalidPageParameterException.class, () -> ValidationUtils.validatePageParam("0"));
        assertThrows(InvalidPageParameterException.class, () -> ValidationUtils.validatePageParam("-5"));
        assertThrows(InvalidPageParameterException.class, () -> ValidationUtils.validatePageParam("abc"));
    }

    @Test
    void sanitizeFilter_shouldTrim_whenNotBlank() {
        assertEquals("hello", ValidationUtils.sanitizeFilter("  hello "));
    }

    @Test
    void sanitizeFilter_shouldReturnNull_whenBlankOrNull() {
        assertNull(ValidationUtils.sanitizeFilter(null));
        assertNull(ValidationUtils.sanitizeFilter("  "));
    }
}

