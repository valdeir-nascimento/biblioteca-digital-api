package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.domain.port.in.EmailValidatorUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;

public class EmailValidatorUseCase implements EmailValidatorUseCasePort {

    private final UserRepositoryPort userRepositoryPort;

    public EmailValidatorUseCase(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void validationEmailExists(String email) {
        userRepositoryPort.validateEmailExists(email);
    }

}
