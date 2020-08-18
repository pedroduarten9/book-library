package com.neto.pedro.booklibrary.error.exception;

import com.neto.pedro.booklibrary.error.ErrorCode;

public class BookConflictException extends BaseException {

    public BookConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
