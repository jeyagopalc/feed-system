package com.example.shared.error;

import com.example.shared.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorTracker {
    private final List<Error> errors = new ArrayList<>();

    public ErrorTracker() {

    }

    public ErrorTracker(final Error error) {
        this.errors.add(error);
    }

    public ErrorTracker(final BaseException exception) {
        if(exception.getData() != null) {
            this.errors.add(new Error(exception.getErrorCode(), exception.getMessage(), exception.getData()));
        } else {
            this.errors.add(new Error(exception.getErrorCode(), exception.getMessage()));
        }
    }

    public void addError(final Error error) {
        this.addError(error);
    }

    public void addError(final ErrorCodes errorCodes, final Object... args) {
        this.addError(new Error(errorCodes.getCode(), errorCodes.getDescription()));
    }

    public List<Error> getErrors() {
        return errors;
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }


}
