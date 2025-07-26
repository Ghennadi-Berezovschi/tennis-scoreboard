package com.example.tennisscoreboard.service;

import com.example.tennisscoreboard.exception.InvalidInputException;
import com.example.tennisscoreboard.exception.InvalidPageParameterException;
import com.example.tennisscoreboard.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void isInvalidInput_shouldReturnFalse_whenValidNames() {
        assertFalse(ValidationUtil.isInvalidInput("Alice", "Bob"));
    }

    @Test
    void isInvalidInput_shouldReturnTrue_whenNamesAreNullOrBlankOrSame() {
        assertTrue(ValidationUtil.isInvalidInput(null, "Bob"));
        assertTrue(ValidationUtil.isInvalidInput("Alice", null));
        assertTrue(ValidationUtil.isInvalidInput("  ", "Bob"));
        assertTrue(ValidationUtil.isInvalidInput("Alice", "  "));
        assertTrue(ValidationUtil.isInvalidInput("Same", "Same"));
    }

    @Test
    void validateAndParseUUID_shouldReturnUUID_whenValid() {
        String uuidStr = UUID.randomUUID().toString();
        UUID parsed = ValidationUtil.validateAndParseUUID(uuidStr);
        assertEquals(uuidStr, parsed.toString());
    }

    @Test
    void validateAndParseUUID_shouldThrow_whenInvalid() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateAndParseUUID(null));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateAndParseUUID(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateAndParseUUID("invalid-uuid"));
    }

    @Test
    void validateRequired_shouldPass_whenValid() {
        assertDoesNotThrow(() -> ValidationUtil.validateRequired("value", "paramName"));
    }

    @Test
    void validateRequired_shouldThrow_whenNullOrBlank() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRequired(null, "testParam"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRequired("", "testParam"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRequired("   ", "testParam"));
    }

    @Test
    void validateRequiredParams_shouldPass_whenAllValid() {
        assertDoesNotThrow(() -> ValidationUtil.validateRequiredParams("one", "two"));
    }

    @Test
    void validateRequiredParams_shouldThrow_whenAnyMissingOrBlank() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRequiredParams("ok", null));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRequiredParams("", "ok"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRequiredParams(" "));
    }

    @Test
    void validatePageParam_shouldReturnParsedValue_whenValid() {
        assertEquals(3, ValidationUtil.validatePageParam("3"));
    }

    @Test
    void validatePageParam_shouldReturn1_whenBlankOrNull() {
        assertEquals(1, ValidationUtil.validatePageParam(null));
        assertEquals(1, ValidationUtil.validatePageParam(" "));
    }

    @Test
    void validatePageParam_shouldThrow_whenNegativeOrNonNumber() {
        assertThrows(InvalidPageParameterException.class, () -> ValidationUtil.validatePageParam("0"));
        assertThrows(InvalidPageParameterException.class, () -> ValidationUtil.validatePageParam("-5"));
        assertThrows(InvalidPageParameterException.class, () -> ValidationUtil.validatePageParam("abc"));
    }

    @Test
    void validateFilterParam_shouldReturnTrimmed_whenValid() {
        Optional<String> result = ValidationUtil.validateFilterParam("  hello ");
        assertTrue(result.isPresent());
        assertEquals("hello", result.get());
    }

    @Test
    void validateFilterParam_shouldReturnEmpty_whenBlankOrNull() {
        assertTrue(ValidationUtil.validateFilterParam(null).isEmpty());
        assertTrue(ValidationUtil.validateFilterParam("   ").isEmpty());
    }
}
