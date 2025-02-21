package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.domain.port.in.UserRegisterUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.EmailValidatorUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;

public class UserRegisterUseCase implements UserRegisterUseCasePort {

    private final UserRepositoryPort userRepositoryPort;
    private final EmailValidatorUseCasePort emailValidatorUseCasePort;

    public UserRegisterUseCase(UserRepositoryPort userRepositoryPort, EmailValidatorUseCasePort emailValidatorUseCasePort) {
        this.userRepositoryPort = userRepositoryPort;
        this.emailValidatorUseCasePort = emailValidatorUseCasePort;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        emailValidatorUseCasePort.validationEmailExists(userDTO.email());
        return userRepositoryPort.create(userDTO);
    }
}
