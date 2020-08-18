package com.neto.pedro.booklibrary.service.impl;

import com.neto.pedro.booklibrary.domain.EntityStatus;
import com.neto.pedro.booklibrary.domain.author.Author;
import com.neto.pedro.booklibrary.error.ErrorCode;
import com.neto.pedro.booklibrary.error.exception.NotFoundException;
import com.neto.pedro.booklibrary.repository.AuthorRepository;
import com.neto.pedro.booklibrary.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public Author readAuthorById(UUID id) {
        return authorRepository.findByIdAndStatusNot(id, EntityStatus.DELETED)
                .orElseThrow(() -> {
                    log.info("Could not find any active author with the id: {}", id);
                    return new NotFoundException(ErrorCode.AUTHOR_NOT_FOUND);
                });
    }
}
