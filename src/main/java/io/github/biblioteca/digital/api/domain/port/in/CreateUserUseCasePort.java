package io.github.biblioteca.digital.api.domain.port.in;

import io.github.biblioteca.digital.api.common.dto.UserDTO;

public interface CreateUserUseCasePort {

    UserDTO create(UserDTO userDTO);
}
