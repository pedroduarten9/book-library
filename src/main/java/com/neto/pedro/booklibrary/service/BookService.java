package com.neto.pedro.booklibrary.service;

import com.neto.pedro.booklibrary.dto.book.BookDto;

import java.util.UUID;

public interface BookService {

    BookDto registerBook(BookDto bookDto);

    BookDto getBook(UUID id);
}
