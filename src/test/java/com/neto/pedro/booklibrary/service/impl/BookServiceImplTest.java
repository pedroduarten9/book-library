package com.neto.pedro.booklibrary.service.impl;

import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.domain.book.Book;
import com.neto.pedro.booklibrary.dto.author.AuthorDto;
import com.neto.pedro.booklibrary.dto.book.BookDto;
import com.neto.pedro.booklibrary.error.exception.BookConflictException;
import com.neto.pedro.booklibrary.error.exception.NotFoundException;
import com.neto.pedro.booklibrary.repository.BookRepository;
import com.neto.pedro.booklibrary.service.AuthorService;
import com.neto.pedro.booklibrary.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplTest {
    private static final String BOOK_TITLE = "title";

    @Autowired
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testRegisterBook() {
        UUID authorId = UUID.randomUUID();
        Author author = new Author();
        author.setId(authorId);
        author.setName("Author 1");

        AuthorDto requestAuthor = new AuthorDto();
        requestAuthor.setId(authorId);

        Book book = createBookWithAuthor(authorId, UUID.randomUUID());

        when(authorService.readAuthorById(any()))
                .thenReturn(author);
        when(bookRepository.save(any()))
                .thenReturn(book);
        when(bookRepository.existsByIsbnAndStatusNot(any(), any()))
                .thenReturn(false);

        BookDto requestBook = new BookDto();
        requestBook.setTitle(BOOK_TITLE);
        requestBook.setDescription("Description");
        requestBook.setAuthor(requestAuthor);

        BookDto bookDto = bookService.registerBook(requestBook);

        assertThat(bookDto.getAuthor().getId()).isEqualTo(authorId);
        assertThat(bookDto.getTitle()).isEqualTo(BOOK_TITLE);
    }

    @Test(expected = BookConflictException.class)
    public void testRegisterBookExistent() {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        author.setId(id);
        author.setName("Author 1");

        AuthorDto requestAuthor = new AuthorDto();
        requestAuthor.setId(id);

        when(authorService.readAuthorById(any()))
                .thenReturn(author);
        when(bookRepository.save(any()))
                .thenReturn(new Book());
        when(bookRepository.existsByIsbnAndStatusNot(any(), any()))
                .thenReturn(true);

        BookDto requestBook = new BookDto();
        requestBook.setTitle(BOOK_TITLE);
        requestBook.setDescription("Description");
        requestBook.setAuthor(requestAuthor);

        bookService.registerBook(requestBook);
    }

    @Test
    public void testGetBook() {
        UUID id = UUID.randomUUID();

        Book book = createBookWithAuthor(UUID.randomUUID(), id);

        when(bookRepository.findByIdAndStatusNot(any(), any()))
                .thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getBook(id);

        assertThat(bookDto.getId()).isEqualTo(book.getId());
        assertThat(bookDto.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
    }

    @Test(expected = NotFoundException.class)
    public void testGetBookNotFound() {
        when(bookRepository.findByIdAndStatusNot(any(), any()))
                .thenReturn(Optional.empty());

        bookService.getBook(UUID.randomUUID());
    }

    private Book createBookWithAuthor(UUID authorId, UUID bookId) {
        Author author = new Author();
        author.setId(authorId);
        author.setName("Author 1");
        Book book = new Book();
        book.setId(bookId);
        book.setTitle(BOOK_TITLE);
        book.setIsbn("Isbn");
        book.setAuthor(author);
        return book;
    }
}