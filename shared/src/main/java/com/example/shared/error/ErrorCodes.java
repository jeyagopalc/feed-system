package com.example.shared.error;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.MessageFormat;

public enum ErrorCodes {

    FEED_001("User not authenticated"),
    FEED_002("Field {0} cannot be missing or empty"),
    FEED_003("Field {0} should not be non-negative integer"),
    FEED_004("The {0} is invalid, errors {}"),
    FEED_005("Error occurred while saving articles"),
    FEED_006("Error occurred while compressing data articles"),
    FEED_007("Error occurred while decompressing the data"),
    FEED_008("Error occurred while retrieving articles"),
    FEED_009("{0} is not a valid {1} param"),
    FEED_010("User not found"),
    FEED_011("Failed to create user");;

    private final String description;

    ErrorCodes(final String description) {
        this.description = description;
    }

    @JsonProperty("desc")
    public String getDescription() {
        return description;
    }

    @JsonProperty("code")
    public String getCode() {
        return name();
    }

    public String getLogMessage(final Object... args) {
        return String.format("%s: %s", getCode(), MessageFormat.format(this.description, args));
    }

    public String getDetailedDescription(final Object... args) {
        return MessageFormat.format(this.description, args);
    }
}
