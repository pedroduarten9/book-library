package com.neto.pedro.booklibrary.service.impl;

import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.domain.book.Book;
import com.neto.pedro.booklibrary.dto.author.AuthorDto;
import com.neto.pedro.booklibrary.dto.book.BookDto;
import com.neto.pedro.booklibrary.error.exception.BookConflictException;
import com.neto.pedro.booklibrary.repository.BookRepository;
import com.neto.pedro.booklibrary.service.AuthorService;
import com.neto.pedro.booklibrary.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testRegisterBook() {
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
                .thenReturn(false);

        String title = "Title";
        BookDto requestBook = new BookDto();
        requestBook.setTitle(title);
        requestBook.setDescription("Description");
        requestBook.setAuthor(requestAuthor);

        BookDto bookDto = bookService.registerBook(requestBook);

        assertThat(bookDto.getAuthor().getId()).isEqualTo(id);
        assertThat(bookDto.getTitle()).isEqualTo(title);
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

        String title = "Title";
        BookDto requestBook = new BookDto();
        requestBook.setTitle(title);
        requestBook.setDescription("Description");
        requestBook.setAuthor(requestAuthor);

        BookDto bookDto = bookService.registerBook(requestBook);
    }
}