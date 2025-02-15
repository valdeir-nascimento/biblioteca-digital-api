package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.UsuarioDTO;
import io.github.biblioteca.digital.api.domain.port.in.UsuarioUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.UsuarioRepositoryPort;

public class UsuarioUseCase implements UsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        return usuarioRepositoryPort.create(usuarioDTO);
    }

}
