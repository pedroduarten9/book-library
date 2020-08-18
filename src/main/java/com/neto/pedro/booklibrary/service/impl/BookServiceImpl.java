package com.neto.pedro.booklibrary.service.impl;

import com.neto.pedro.booklibrary.converter.BookConverter;
import com.neto.pedro.booklibrary.domain.EntityStatus;
import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.domain.book.Book;
import com.neto.pedro.booklibrary.dto.book.BookDto;
import com.neto.pedro.booklibrary.error.ErrorCode;
import com.neto.pedro.booklibrary.error.exception.BookConflictException;
import com.neto.pedro.booklibrary.error.exception.NotFoundException;
import com.neto.pedro.booklibrary.repository.BookRepository;
import com.neto.pedro.booklibrary.service.AuthorService;
import com.neto.pedro.booklibrary.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookConverter bookConverter;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookConverter = bookConverter;
    }

    @Override
    @Transactional
    public BookDto registerBook(BookDto bookDto) {
        validateRegisterBook(bookDto);
        Author author = authorService.readAuthorById(bookDto.getAuthor().getId());
        Book book = bookRepository.save(bookConverter.fromDto(bookDto, author, EntityStatus.ACTIVE));

        log.info("Success creating book");
        return bookConverter.toDto(book);
    }

    private void validateRegisterBook(BookDto bookDto) {
        if (bookRepository.existsByIsbnAndStatusNot(bookDto.getIsbn(), EntityStatus.DELETED)) {
            log.info("ISBN provided: {} is already registered", bookDto.getIsbn());
            throw new BookConflictException(ErrorCode.ISBN_CONFLICT);
        }
    }

    @Override
    public BookDto getBook(UUID id) {
        Book book = bookRepository.findByIdAndStatusNot(id, EntityStatus.DELETED)
                .orElseThrow(() -> {
                    log.info("Could not find any active book with the id: {}", id);
                    return new NotFoundException(ErrorCode.BOOK_NOT_FOUND);
                });

        log.info("Success reading book");
        return bookConverter.toDto(book);
    }
}
