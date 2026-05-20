package com.example.confighub.util;

// Pattern 33, 47: org.springframework.lang.Nullable/NonNull (become JSpecify annotations in 4.0)
import org.springframework.lang.Nullable;
import org.springframework.lang.NonNull;

public class NullableUtils {

    public static String normalizeKey(@Nullable String key) {
        if (key == null) {
            return "";
        }
        return key.strip().toLowerCase();
    }

    @NonNull
    public static String requireNonEmpty(@Nullable String value, @NonNull String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty");
        }
        return value;
    }
}
