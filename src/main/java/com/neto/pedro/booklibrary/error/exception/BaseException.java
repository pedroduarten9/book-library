package com.neto.pedro.booklibrary.error.exception;

import com.neto.pedro.booklibrary.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
}
