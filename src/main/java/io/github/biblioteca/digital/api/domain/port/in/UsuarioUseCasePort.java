package io.github.biblioteca.digital.api.domain.port.in;

import io.github.biblioteca.digital.api.common.dto.UsuarioDTO;

public interface UsuarioUseCasePort {

    UsuarioDTO create(UsuarioDTO usuarioDTO);

}
