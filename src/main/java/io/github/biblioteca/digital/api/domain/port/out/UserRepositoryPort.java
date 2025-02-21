package io.github.biblioteca.digital.api.domain.port.out;

import io.github.biblioteca.digital.api.common.dto.UserDTO;

public interface UserRepositoryPort {

    UserDTO create(UserDTO userDTO);

    void validateUserExists(Integer userId);

    void validateEmailExists(String email);
}
