package com.neto.pedro.booklibrary.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GENERIC_BAD_REQUEST(HttpStatus.BAD_REQUEST, 1, "invalid request"),
    ;

    private HttpStatus httpStatus;
    private int internalCode;
    private String description;
}
