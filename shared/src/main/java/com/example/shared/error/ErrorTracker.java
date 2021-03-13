package com.example.shared.error;

import com.example.shared.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

import java.text.MessageFormat;
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

    @JsonIgnore
    public void addError(final Error error) {
        this.errors.add(error);
    }

    @JsonIgnore
    public void addError(final ErrorCodes errorCodes, final Object... args) {
        this.addError(new Error(errorCodes.getCode(), MessageFormat.format(errorCodes.getDescription(), args)));
    }

    @JsonIgnore
    public void addError(final ErrorCodes errorCodes) {
        this.addError(new Error(errorCodes.getCode(), errorCodes.getDescription()));
    }

    public List<Error> getErrors() {
        return errors;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return errors.isEmpty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("errors", errors)
                .toString();
    }
}
