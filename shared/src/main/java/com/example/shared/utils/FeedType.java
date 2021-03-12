package com.example.shared.utils;

public enum FeedType {
    PARTIAL("Partial"), FULL("Full");

    private final String feedType;

    FeedType(final String feedType) {
        this.feedType = feedType;
    }

    public static FeedType fromString(final String param) {
        try {
            String toUpper = param.toUpperCase();
            return valueOf(toUpper);
        } catch (Exception e) {
            return null;
        }
    }

    public String toString() {
        return this.feedType;
    }
}
