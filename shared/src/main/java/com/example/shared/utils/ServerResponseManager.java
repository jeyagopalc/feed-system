package com.example.shared.utils;

import com.example.shared.error.ErrorTracker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ServerResponseManager {

    public static ResponseEntity<Object> constructSuccessResponse(final Object body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static ResponseEntity<Object> constructBadRequestResponse(final ErrorTracker errorTracker) {
        return new ResponseEntity<>(errorTracker, HttpStatus.BAD_REQUEST);
    }
}
