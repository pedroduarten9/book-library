package com.neto.pedro.booklibrary.repository;

import com.neto.pedro.booklibrary.domain.EntityStatus;
import com.neto.pedro.booklibrary.domain.author.Author;
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
public class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindByIdAndStatusNotGoodPath() {
        Author author = new Author();
        author.setName("John Doe Test");
        author.setStatus(EntityStatus.ACTIVE);

        UUID id = (UUID) testEntityManager.persistAndGetId(author);
        testEntityManager.flush();
        author.setId(id);

        Optional<Author> found = authorRepository.findByIdAndStatusNot(id, EntityStatus.DELETED);

        assertThat(found).isNotEmpty();
        assertThat(found.get().getId())
                .isEqualTo(author.getId());
        assertThat(found.get().getStatus())
                .isEqualTo(EntityStatus.ACTIVE);
    }

    @Test
    public void testFindByIdAndStatusNotFound() {
        Author author = new Author();
        author.setName("John Doe Test");
        author.setStatus(EntityStatus.ACTIVE);

        UUID id = (UUID) testEntityManager.persistAndGetId(author);
        testEntityManager.flush();
        author.setId(id);

        Optional<Author> found = authorRepository.findByIdAndStatusNot(UUID.randomUUID(), EntityStatus.DELETED);

        assertThat(found).isEmpty();
    }

    @Test
    public void testFindByIdAndStatusNotEmpty() {
        Author author = new Author();
        author.setName("John Doe Test");
        author.setStatus(EntityStatus.DELETED);

        UUID id = (UUID) testEntityManager.persistAndGetId(author);
        testEntityManager.flush();
        author.setId(id);

        Optional<Author> found = authorRepository.findByIdAndStatusNot(id, EntityStatus.DELETED);

        assertThat(found).isEmpty();
    }

    @Test
    public void testFindByIdAndStatusNotNoValues() {
        Optional<Author> found = authorRepository.findByIdAndStatusNot(UUID.randomUUID(), EntityStatus.DELETED);

        assertThat(found).isEmpty();
    }
}