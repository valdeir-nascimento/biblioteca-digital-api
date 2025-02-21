package io.github.biblioteca.digital.api.infrastructure.repository;

import io.github.biblioteca.digital.api.infrastructure.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
