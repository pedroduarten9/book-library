package com.neto.pedro.booklibrary.domain.book;

import com.neto.pedro.booklibrary.domain.BaseEntity;
import com.neto.pedro.booklibrary.domain.author.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @ManyToOne
    private Author author;
}
