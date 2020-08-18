package com.neto.pedro.booklibrary.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GENERIC_BAD_REQUEST(HttpStatus.BAD_REQUEST, 1, "invalid request"),

    AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, 1, "Author not found"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, 2, "Book not found"),

    ISBN_CONFLICT(HttpStatus.CONFLICT, 1, "ISBN already exists"),
    ;

    private HttpStatus httpStatus;
    private int internalCode;
    private String description;
}
