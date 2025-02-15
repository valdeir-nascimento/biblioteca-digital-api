package io.github.biblioteca.digital.api.mock;

import io.github.biblioteca.digital.api.common.dto.UsuarioDTO;

public class UsuarioMockFactory {

    private UsuarioMockFactory() {}

    public static UsuarioDTO getCriarUsuarioDTO() {
        return new UsuarioDTO(null, "João da Silva", "joao.silva@gmail.com");
    }

    public static UsuarioDTO getUsuarioRegistrado() {
        return new UsuarioDTO(1, "João da Silva", "joao.silva@gmail.com");
    }

}
