package com.neto.pedro.booklibrary.advice;

import com.neto.pedro.booklibrary.error.ErrorCode;
import com.neto.pedro.booklibrary.error.ResponseError;
import com.neto.pedro.booklibrary.error.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseError> handleMethodArgumentNotValidException() {
        return ResponseEntity.status(ErrorCode.GENERIC_BAD_REQUEST.getHttpStatus())
                .body(new ResponseError(ErrorCode.GENERIC_BAD_REQUEST));
    }

    @ExceptionHandler(BaseException.class)
    ResponseEntity<ResponseError> handleInvalidTimeFrameException(BaseException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(new ResponseError(ex.getErrorCode()));
    }
}
