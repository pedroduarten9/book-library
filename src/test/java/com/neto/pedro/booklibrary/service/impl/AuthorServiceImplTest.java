package com.neto.pedro.booklibrary.service.impl;

import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.error.exception.NotFoundException;
import com.neto.pedro.booklibrary.repository.AuthorRepository;
import com.neto.pedro.booklibrary.service.AuthorService;
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
public class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    public void testReadAuthorById() {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        author.setId(id);

        when(authorRepository.findByIdAndStatusNot(any(), any()))
                .thenReturn(Optional.of(author));

        Author found = authorService.readAuthorById(id);

        assertThat(found.getId()).isEqualTo(author.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testReadAuthorByIdNotFound() {
        when(authorRepository.findByIdAndStatusNot(any(), any()))
                .thenReturn(Optional.empty());

        authorService.readAuthorById(UUID.randomUUID());
    }
}