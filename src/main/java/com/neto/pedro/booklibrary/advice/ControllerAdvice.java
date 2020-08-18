package com.neto.pedro.booklibrary.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.neto.pedro.booklibrary.error.ErrorCode;
import com.neto.pedro.booklibrary.error.ResponseError;
import com.neto.pedro.booklibrary.error.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseError> handleMethodArgumentNotValidException() {
        return ResponseEntity.status(ErrorCode.GENERIC_BAD_REQUEST.getHttpStatus())
                .body(new ResponseError(ErrorCode.GENERIC_BAD_REQUEST));
    }

    @ExceptionHandler(InvalidFormatException.class)
    ResponseEntity<ResponseError> handleInvalidFormatException() {
        return ResponseEntity.status(ErrorCode.GENERIC_BAD_REQUEST.getHttpStatus())
                .body(new ResponseError(ErrorCode.GENERIC_BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ResponseError> handleMethodArgumentTypeMismatchException() {
        return ResponseEntity.status(ErrorCode.GENERIC_BAD_REQUEST.getHttpStatus())
                .body(new ResponseError(ErrorCode.GENERIC_BAD_REQUEST));
    }

    @ExceptionHandler(BaseException.class)
    ResponseEntity<ResponseError> handleInvalidTimeFrameException(BaseException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(new ResponseError(ex.getErrorCode()));
    }
}
