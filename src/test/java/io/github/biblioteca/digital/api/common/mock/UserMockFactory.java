package io.github.biblioteca.digital.api.common.mock;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.infrastructure.model.User;

public class UserMockFactory {

    private UserMockFactory() {}

    public static UserDTO getCreateUser() {
        return new UserDTO(null, "João da Silva", "joao.silva@gmail.com");
    }

    public static UserDTO getUserSaved() {
        return new UserDTO(1, "João da Silva", "joao.silva@gmail.com");
    }

    public static User getUserModel() {
        User user = new User();
        user.setId(1L);
        user.setName("João da Silva");
        user.setEmail("joao.silva@gmail.com");
        return user;
    }

}
