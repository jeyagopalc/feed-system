package com.example.shared.exception;

import com.example.shared.error.ErrorCodes;
import lombok.Data;

@Data
public class BaseException extends Exception {
    private final String errorCode;
    private final Object data;

    public BaseException() {
        super();
        this.errorCode = null;
        this.data = null;
    }

    public BaseException(final String errorCode, final String exceptionMsg) {
        super(exceptionMsg);
        this.errorCode = errorCode;
        this.data = null;
    }

    public BaseException(final String errorCode, final String exceptionMsg, final Object data) {
        super(exceptionMsg);
        this.errorCode = errorCode;
        this.data = data;
    }

    public BaseException(final ErrorCodes errorCodes) {
        this(errorCodes.getCode(), errorCodes.getDescription());

    }

    public BaseException(final ErrorCodes errorCodes, final Throwable cause) {
        this(errorCodes.getCode(), errorCodes.getDescription(), cause);
    }

}
