package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.domain.port.in.CreateUserUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;

public class CreateUserUseCase implements CreateUserUseCasePort {

    private final UserRepositoryPort userRepositoryPort;

    public CreateUserUseCase(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return userRepositoryPort.create(userDTO);
    }
}
