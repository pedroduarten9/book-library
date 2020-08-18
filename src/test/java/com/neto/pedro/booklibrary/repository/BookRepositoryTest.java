package com.neto.pedro.booklibrary.repository;

import com.neto.pedro.booklibrary.domain.EntityStatus;
import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.domain.book.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {
    private static final String ISBN_TEST = "ISBNTEST";
    private static final String TITLE_TEST = "TITLE_TEST";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    private Author author;

    @Before
    public void setUp() {
        Author author = new Author();
        author.setName("John Doe Test");
        author.setStatus(EntityStatus.ACTIVE);

        testEntityManager.persist(author);
        testEntityManager.flush();

        this.author = author;
    }

    @Test
    public void testExistsByIsbnAndStatusNot() {
        Book book = createBookWithStatus(EntityStatus.ACTIVE);

        testEntityManager.persist(book);
        testEntityManager.flush();

        boolean exists = bookRepository.existsByIsbnAndStatusNot(ISBN_TEST, EntityStatus.DELETED);

        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByIsbnAndStatusNotSoftDelete() {
        Book book = createBookWithStatus(EntityStatus.DELETED);

        testEntityManager.persist(book);
        testEntityManager.flush();

        boolean exists = bookRepository.existsByIsbnAndStatusNot(ISBN_TEST, EntityStatus.DELETED);

        assertThat(exists).isFalse();
    }

    @Test
    public void testExistsByIsbnAndStatusNotWrongIsbn() {
        Book book = createBookWithStatus(EntityStatus.ACTIVE);

        testEntityManager.persist(book);
        testEntityManager.flush();

        boolean exists = bookRepository.existsByIsbnAndStatusNot(ISBN_TEST + "FAIL", EntityStatus.DELETED);

        assertThat(exists).isFalse();
    }

    @Test
    public void testExistsByIsbnAndStatusNotEmpty() {
        boolean exists = bookRepository.existsByIsbnAndStatusNot(ISBN_TEST, EntityStatus.DELETED);

        assertThat(exists).isFalse();
    }

    @Test
    public void testFindByIdAndStatusNot() {
        Book book = createBookWithStatus(EntityStatus.ACTIVE);

        UUID id = (UUID) testEntityManager.persistAndGetId(book);
        testEntityManager.flush();

        Optional<Book> found = bookRepository.findByIdAndStatusNot(id, EntityStatus.DELETED);

        assertThat(found).isNotEmpty();
    }

    @Test
    public void testFindByIdAndStatusNotNotFound() {
        Book book = createBookWithStatus(EntityStatus.DELETED);

        UUID id = (UUID) testEntityManager.persistAndGetId(book);
        testEntityManager.flush();

        Optional<Book> found = bookRepository.findByIdAndStatusNot(id, EntityStatus.DELETED);

        assertThat(found).isEmpty();
    }

    @Test
    public void testFindByIdAndStatusNotNotFoundEmpty() {
        Optional<Book> found = bookRepository.findByIdAndStatusNot(UUID.randomUUID(), EntityStatus.DELETED);

        assertThat(found).isEmpty();
    }

    private Book createBookWithStatus(EntityStatus status) {
        Book book = new Book();
        book.setIsbn(ISBN_TEST);
        book.setTitle(TITLE_TEST);
        book.setAuthor(author);
        book.setStatus(status);
        return book;
    }
}