package com.neto.pedro.booklibrary.controller;

import com.neto.pedro.booklibrary.dto.book.BookDto;
import com.neto.pedro.booklibrary.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping(value = BookController.BASE_URL,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class BookController {
    static final String BASE_URL = "/books";

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation("Register a new book")
    @PostMapping
    public ResponseEntity<BookDto> registerBook(@NotNull @Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.registerBook(bookDto));
    }
}
