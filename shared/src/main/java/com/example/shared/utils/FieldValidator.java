package com.example.shared.utils;

import com.example.shared.error.ErrorTracker;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

import static com.example.shared.error.ErrorCodes.FEED_002;
import static com.example.shared.error.ErrorCodes.FEED_009;

public class FieldValidator {
    private FieldValidator() {

    }

    public static void validateNonEmptyString(final String fieldName, final String value,
                                              final ErrorTracker errorTracker) {
        if (StringUtils.isBlank(value)) {
            errorTracker.addError(FEED_002, fieldName);
        }
    }

    public static void validateNonNegativeInteger(final String fieldName, final int value,
                                              final ErrorTracker errorTracker) {
        if (value < 0) {
            errorTracker.addError(FEED_002, fieldName);
        }
    }

    public static <T> void validateAllowedParams(final String fieldName, final T value,
                                                 final Set<T> allowedValues,
                                                 final ErrorTracker errorTracker) {
        if (!allowedValues.contains(value)) {
            errorTracker.addError(FEED_009, value, fieldName);

        }
    }
}
