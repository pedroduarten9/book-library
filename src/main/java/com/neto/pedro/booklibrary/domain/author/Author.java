package com.neto.pedro.booklibrary.domain.author;

import com.neto.pedro.booklibrary.domain.BaseEntity;
import com.neto.pedro.booklibrary.domain.book.Book;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Author extends BaseEntity {

    @NotBlank
    private String name;

    @OneToMany
    private Set<Book> books;
}
