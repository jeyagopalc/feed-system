package com.example.userService.utils;

import com.example.shared.model.User;

public final class UserTestUtils {

    public static final String TEST_FIRST_NAME = "James";
    public static final String TEST_LAST_NAME = "vasanth";
    public static final String TEST_EMAIL = "james@gmail.com";
    public static final String TEST_PASSWORD = "A4567789";
    public static final String TEST_USER_ID = "jamesv";
    public static final String TEST_ENCRYPTED_PASSWORD = "fmefmfekf";

    private UserTestUtils() {

    }

    public static User createUser() {
        User user = new User();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);
        user.setUserId(TEST_USER_ID);
        user.setEncryptedPassword(TEST_ENCRYPTED_PASSWORD);
        return user;
    }
}
