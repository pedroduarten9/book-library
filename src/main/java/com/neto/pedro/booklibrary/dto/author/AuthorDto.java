package com.neto.pedro.booklibrary.dto.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neto.pedro.booklibrary.domain.book.Book;
import com.neto.pedro.booklibrary.dto.BaseEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDto extends BaseEntityDto {

    @NotBlank
    private String name;

    private Set<Book> books;
}
