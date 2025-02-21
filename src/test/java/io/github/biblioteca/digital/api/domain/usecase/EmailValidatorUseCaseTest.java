package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.exception.EmailConflictException;
import io.github.biblioteca.digital.api.common.util.MessagesUtils;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailValidatorUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private EmailValidatorUseCase emailValidatorUseCase;

    @Test
    public void givenValidEmail_whenValidationEmailExists_thenRepositoryMethodCalled() {
        final var email = "test@example.com";
        emailValidatorUseCase.validationEmailExists(email);
        verify(userRepositoryPort, times(1)).validateEmailExists(anyString());
    }

    @Test
    public void givenExistingEmail_whenValidationEmailExists_thenThrowEmailConflictException() {
        final var email = "existing@example.com";

        doThrow(new EmailConflictException(MessagesUtils.MSG_EMAIL_CONFLICT)).when(userRepositoryPort)
                .validateEmailExists(email);

        assertThrows(EmailConflictException.class, () -> {
            emailValidatorUseCase.validationEmailExists(email);
        });
    }

}