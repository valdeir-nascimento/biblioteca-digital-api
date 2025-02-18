package io.github.biblioteca.digital.api.infrastructure.repository;

import io.github.biblioteca.digital.api.infrastructure.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
