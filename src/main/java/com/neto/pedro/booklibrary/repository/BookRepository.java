package com.neto.pedro.booklibrary.repository;

import com.neto.pedro.booklibrary.domain.EntityStatus;
import com.neto.pedro.booklibrary.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByIsbnAndStatusNot(String isbn, EntityStatus status);
}
