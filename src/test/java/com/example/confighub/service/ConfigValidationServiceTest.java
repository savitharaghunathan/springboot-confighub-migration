package com.example.confighub.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigValidationServiceTest {

    private final ConfigValidationService validationService = new ConfigValidationService();

    @Test
    void validateConfig_validKeyAndValue() {
        List<String> errors = validationService.validateConfig("app.name", "Config Hub");
        assertTrue(errors.isEmpty());
    }

    @Test
    void validateConfig_nullKey() {
        List<String> errors = validationService.validateConfig(null, "value");
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("Key"));
    }

    @Test
    void validateConfig_invalidKeyFormat() {
        List<String> errors = validationService.validateConfig("123invalid", "value");
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("letter"));
    }

    @Test
    void validateConfig_nullValue() {
        List<String> errors = validationService.validateConfig("app.name", null);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("Value"));
    }

    @Test
    void validateConfig_valueTooLong() {
        String longValue = "x".repeat(10001);
        List<String> errors = validationService.validateConfig("app.name", longValue);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("10000"));
    }

    @Test
    void validateConfig_multipleErrors() {
        List<String> errors = validationService.validateConfig(null, null);
        assertEquals(2, errors.size());
    }

    @Test
    void sanitizeValue_stripsWhitespace() {
        assertEquals("hello", validationService.sanitizeValue("  hello  "));
    }

    @Test
    void isValidJsonValue_validObject() {
        assertTrue(validationService.isValidJsonValue("{\"key\":\"value\"}"));
    }

    @Test
    void isValidJsonValue_validArray() {
        assertTrue(validationService.isValidJsonValue("[1,2,3]"));
    }

    @Test
    void isValidJsonValue_invalidJson() {
        assertFalse(validationService.isValidJsonValue("not json"));
    }

    @Test
    void isValidJsonValue_nullValue() {
        assertFalse(validationService.isValidJsonValue(null));
    }
}
