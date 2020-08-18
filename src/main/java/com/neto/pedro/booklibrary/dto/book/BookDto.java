package com.neto.pedro.booklibrary.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neto.pedro.booklibrary.dto.BaseEntityDto;
import com.neto.pedro.booklibrary.dto.author.AuthorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto extends BaseEntityDto {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private AuthorDto author;
}
