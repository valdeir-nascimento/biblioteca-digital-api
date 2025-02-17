package io.github.biblioteca.digital.api.mock;

import io.github.biblioteca.digital.api.common.dto.UserDTO;

public class UserMockFactory {

    private UserMockFactory() {}

    public static UserDTO getCreateUser() {
        return new UserDTO(null, "João da Silva", "joao.silva@gmail.com");
    }

    public static UserDTO getUserSaved() {
        return new UserDTO(1, "João da Silva", "joao.silva@gmail.com");
    }

}
