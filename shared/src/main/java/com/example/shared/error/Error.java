package com.example.shared.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.MessageFormat;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Error {
    private String errorCode;
    private String errorDesc;
    private Object data;

    public Error() {
    }

    public Error(final ErrorCodes errorCodes){
        this.errorCode = errorCodes.getCode();
        this.errorDesc = errorCodes.getDescription();
        this.data = null;
    }

    public Error(final String errorCode, final String errorDesc){
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.data = null;
    }
    public Error(final String errorCode, final String errorDesc, final Object... data){
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.data = data;
    }

    public Error(final ErrorCodes errorCodes, final Object... args){
        this(errorCodes.getCode(), MessageFormat.format(errorCodes.getDescription(), args));
    }

}
