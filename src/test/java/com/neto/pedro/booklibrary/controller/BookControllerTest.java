package com.neto.pedro.booklibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neto.pedro.booklibrary.dto.author.AuthorDto;
import com.neto.pedro.booklibrary.dto.book.BookDto;
import com.neto.pedro.booklibrary.error.ErrorCode;
import com.neto.pedro.booklibrary.error.exception.NotFoundException;
import com.neto.pedro.booklibrary.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    public void testRegisterBook() throws Exception {
        AuthorDto author = new AuthorDto();
        author.setId(UUID.randomUUID());

        BookDto book = new BookDto();
        book.setId(UUID.randomUUID());
        book.setIsbn("Isbn");
        book.setTitle("Title");
        book.setAuthor(author);

        when(bookService.registerBook(any()))
                .thenReturn(book);

        mvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testRegisterBookAuthorNotFound() throws Exception {
        AuthorDto author = new AuthorDto();
        author.setId(UUID.randomUUID());

        BookDto book = new BookDto();
        book.setId(UUID.randomUUID());
        book.setIsbn("Isbn");
        book.setTitle("Title");
        book.setAuthor(author);

        when(bookService.registerBook(any()))
                .thenThrow(new NotFoundException(ErrorCode.AUTHOR_NOT_FOUND));

        mvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testRegisterBookWrongBody() throws Exception {
        AuthorDto author = new AuthorDto();
        author.setId(UUID.randomUUID());

        BookDto book = new BookDto();
        book.setId(UUID.randomUUID());
        book.setTitle("Title");
        book.setAuthor(author);

        when(bookService.registerBook(any()))
                .thenReturn(book);

        mvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetBook() throws Exception {
        AuthorDto author = new AuthorDto();
        author.setId(UUID.randomUUID());

        UUID bookId = UUID.randomUUID();
        BookDto book = new BookDto();
        book.setId(bookId);
        book.setTitle("Title");
        book.setAuthor(author);

        when(bookService.getBook(any()))
                .thenReturn(book);

        mvc.perform(
                get("/books/" + bookId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetBookBadRequest() throws Exception {
        AuthorDto author = new AuthorDto();
        author.setId(UUID.randomUUID());

        BookDto book = new BookDto();
        book.setId(UUID.randomUUID());
        book.setTitle("Title");
        book.setAuthor(author);

        when(bookService.getBook(any()))
                .thenReturn(book);

        mvc.perform(
                get("/books/olaola"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}