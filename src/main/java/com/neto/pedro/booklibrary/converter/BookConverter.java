package com.neto.pedro.booklibrary.converter;

import com.neto.pedro.booklibrary.domain.EntityStatus;
import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.domain.book.Book;
import com.neto.pedro.booklibrary.dto.book.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    private final AuthorConverter authorConverter;

    @Autowired
    public BookConverter(AuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }

    public Book fromDto(BookDto bookDto, Author author, EntityStatus status) {
        Book book = new Book();
        book.setAuthor(author);
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setStatus(status);
        return book;
    }

    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setCreatedDate(book.getCreatedDate());
        bookDto.setLastModifiedDate(book.getLastModifiedDate());
        bookDto.setAuthor(authorConverter.toSummaryDto(book.getAuthor()));
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }
}
