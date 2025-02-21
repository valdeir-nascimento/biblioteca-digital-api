package io.github.biblioteca.digital.api.domain.port.in;

import io.github.biblioteca.digital.api.common.dto.UserDTO;

public interface UserRegisterUseCasePort {

    UserDTO create(UserDTO userDTO);
}
