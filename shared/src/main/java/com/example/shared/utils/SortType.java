package com.example.shared.utils;

public enum SortType {
    ASC, DESC;

    public static SortType fromString(final String param) {
        String toUpper = param.toUpperCase();

        try {
            return valueOf(toUpper);
        } catch (Exception e) {
            return null;
        }
    }

}
