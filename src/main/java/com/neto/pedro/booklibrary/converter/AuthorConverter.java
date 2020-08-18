package com.neto.pedro.booklibrary.converter;

import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.dto.author.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    public AuthorDto toSummaryDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        return authorDto;
    }
}
