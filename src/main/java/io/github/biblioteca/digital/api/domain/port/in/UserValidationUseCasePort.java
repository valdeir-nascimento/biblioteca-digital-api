package io.github.biblioteca.digital.api.domain.port.in;

public interface UserValidationUseCasePort {

    void validateUserExists(Integer userId);

}
