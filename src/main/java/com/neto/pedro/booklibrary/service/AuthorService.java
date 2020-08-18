package com.neto.pedro.booklibrary.service;

import com.neto.pedro.booklibrary.domain.author.Author;

import java.util.UUID;

public interface AuthorService {

    Author readAuthorById(UUID id);
}
