package io.github.biblioteca.digital.api.domain.port.out;

import io.github.biblioteca.digital.api.common.dto.UsuarioDTO;

public interface UsuarioRepositoryPort {

    UsuarioDTO create(UsuarioDTO usuarioDTO);

}
