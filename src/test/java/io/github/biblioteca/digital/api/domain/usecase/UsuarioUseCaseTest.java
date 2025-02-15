package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.UsuarioDTO;
import io.github.biblioteca.digital.api.domain.port.out.UsuarioRepositoryPort;
import io.github.biblioteca.digital.api.mock.UsuarioMockFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    @Test
    void givenValidData_whenRegisteringNewUser_thenUserIsSuccessfullyRegistered() {
        final var usuario = UsuarioMockFactory.getCriarUsuarioDTO();
        final var usuarioRegistrado = UsuarioMockFactory.getUsuarioRegistrado();
        when(usuarioRepositoryPort.create(usuario)).thenReturn(usuarioRegistrado);

        UsuarioDTO result = usuarioUseCase.create(usuario);

        assertNotNull(result.id());
        assertEquals(usuarioRegistrado.nome(), result.nome());
        assertEquals(usuarioRegistrado.email(), result.email());
        verify(usuarioRepositoryPort, times(1)).create(usuario);
    }

}