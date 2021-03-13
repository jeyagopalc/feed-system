package com.example.shared.utils;

import com.example.shared.error.ErrorCodes;
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

    public static ResponseEntity<Object> constructCreateSuccessResponse(final Object body) {
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public static ResponseEntity<Object> constructInternalServerErrorResponse(final ErrorCodes errorCodes,
                                                                              final Object body) {
        final ErrorTracker errorTracker = new ErrorTracker();
        errorTracker.addError(errorCodes, body);
        return new ResponseEntity<>(errorTracker, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
