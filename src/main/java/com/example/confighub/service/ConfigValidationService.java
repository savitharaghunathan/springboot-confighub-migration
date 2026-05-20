package com.example.confighub.service;

// Pattern 33: org.springframework.lang.Nullable (becomes org.jspecify.annotations.Nullable in 4.0)
import org.springframework.lang.Nullable;
// Pattern 47: org.springframework.lang.NonNull (becomes JSpecify in 4.0)
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ConfigValidationService {

    private static final Pattern KEY_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9._-]*$");
    private static final int MAX_VALUE_LENGTH = 10000;

    @NonNull
    public List<String> validateConfig(@Nullable String key, @Nullable String value) {
        List<String> errors = new ArrayList<>();

        if (key == null || key.isBlank()) {
            errors.add("Key must not be empty");
        } else if (!KEY_PATTERN.matcher(key).matches()) {
            errors.add("Key must start with a letter and contain only letters, digits, dots, hyphens, and underscores");
        }

        if (value == null || value.isBlank()) {
            errors.add("Value must not be empty");
        } else if (value.length() > MAX_VALUE_LENGTH) {
            errors.add("Value must not exceed " + MAX_VALUE_LENGTH + " characters");
        }

        return errors;
    }

    @NonNull
    public String sanitizeValue(@NonNull String value) {
        return value.strip();
    }

    public boolean isValidJsonValue(@Nullable String value) {
        if (value == null) {
            return false;
        }
        String trimmed = value.strip();
        return (trimmed.startsWith("{") && trimmed.endsWith("}"))
                || (trimmed.startsWith("[") && trimmed.endsWith("]"));
    }
}
