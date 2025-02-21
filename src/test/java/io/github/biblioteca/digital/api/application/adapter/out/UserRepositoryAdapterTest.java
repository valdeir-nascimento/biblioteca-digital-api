package io.github.biblioteca.digital.api.application.adapter.out;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.common.exception.EmailConflictException;
import io.github.biblioteca.digital.api.common.exception.NotFoundException;
import io.github.biblioteca.digital.api.common.mapper.UserMapper;
import io.github.biblioteca.digital.api.common.mock.UserMockFactory;
import io.github.biblioteca.digital.api.common.util.MessagesUtils;
import io.github.biblioteca.digital.api.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    void givenValidUserDTO_whenCreate_thenReturnCreatedUserDTO() {
        final var inputDto = UserMockFactory.getCreateUser();
        final var userEntity = UserMockFactory.getUserModel();
        final var expectedDto = UserMockFactory.getUserSaved();

        when(userMapper.toEntity(inputDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(expectedDto);

        UserDTO result = userRepositoryAdapter.create(inputDto);

        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(userMapper).toEntity(inputDto);
        verify(userRepository).save(userEntity);
        verify(userMapper).toDTO(userEntity);
    }

    @Test
    void givenExistingUser_whenValidateUserExists_thenDoNotThrowException() {
        final var userId = 1;
        final var userEntity = UserMockFactory.getUserModel();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        assertDoesNotThrow(() -> userRepositoryAdapter.validateUserExists(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void givenNonExistingUser_whenValidateUserExists_thenThrowNotFoundException() {
        final var userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userRepositoryAdapter.validateUserExists(userId));
        assertEquals(MessagesUtils.MSG_USER_NOT_FOUND, exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void givenNonExistingEmail_whenValidateEmailExists_thenNoExceptionThrown() {
        final var email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userRepositoryAdapter.validateEmailExists(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void givenExistingEmail_whenValidateEmailExists_thenThrowsEmailConflictException() {
        final var userModel = UserMockFactory.getUserModel();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userModel));

        EmailConflictException exception = assertThrows(EmailConflictException.class,
                () -> userRepositoryAdapter.validateEmailExists(userModel.getEmail()));

        assertEquals(MessagesUtils.MSG_EMAIL_CONFLICT, exception.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

}