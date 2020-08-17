package com.neto.pedro.booklibrary.error;

import lombok.Getter;

@Getter
public class ResponseError {

    private final int internalCode;
    private final String errorDescription;

    public ResponseError(ErrorCode errorCode) {
        internalCode = errorCode.getInternalCode();
        errorDescription = errorCode.getDescription();
    }
}
