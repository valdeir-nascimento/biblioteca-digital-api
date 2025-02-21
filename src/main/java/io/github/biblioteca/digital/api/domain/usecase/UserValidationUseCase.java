package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;

public class UserValidationUseCase implements UserValidationUseCasePort {

    private final UserRepositoryPort userRepositoryPort;

    public UserValidationUseCase(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void validateUserExists(Integer userId) {
        userRepositoryPort.validateUserExists(userId);
    }

}
