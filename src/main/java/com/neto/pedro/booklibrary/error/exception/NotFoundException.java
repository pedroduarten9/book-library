package com.neto.pedro.booklibrary.error.exception;

import com.neto.pedro.booklibrary.error.ErrorCode;

public class NotFoundException extends BaseException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
