package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;
import io.github.biblioteca.digital.api.common.mock.UserMockFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void givenValidData_whenRegisteringNewUser_thenUserIsSuccessfullyRegistered() {
        final var user = UserMockFactory.getCreateUser();
        final var userSaved = UserMockFactory.getUserSaved();
        when(userRepositoryPort.create(user)).thenReturn(userSaved);

        UserDTO result = createUserUseCase.create(user);

        assertNotNull(result.id());
        assertEquals(userSaved.name(), result.name());
        assertEquals(userSaved.email(), result.email());
        verify(userRepositoryPort, times(1)).create(user);
    }

}