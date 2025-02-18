package io.github.biblioteca.digital.api.infrastructure.repository;

import io.github.biblioteca.digital.api.infrastructure.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
