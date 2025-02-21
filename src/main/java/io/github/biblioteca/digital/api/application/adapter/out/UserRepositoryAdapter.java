package io.github.biblioteca.digital.api.application.adapter.out;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.common.exception.EmailConflictException;
import io.github.biblioteca.digital.api.common.exception.NotFoundException;
import io.github.biblioteca.digital.api.common.mapper.UserMapper;
import io.github.biblioteca.digital.api.common.util.MessagesUtils;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;
import io.github.biblioteca.digital.api.infrastructure.model.User;
import io.github.biblioteca.digital.api.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO create(UserDTO userDTO) {
        User entity = userMapper.toEntity(userDTO);
        entity = userRepository.save(entity);
        return userMapper.toDTO(entity);
    }

    @Override
    public void validateUserExists(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(MessagesUtils.MSG_USER_NOT_FOUND));
    }

    @Override
    public void validateEmailExists(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailConflictException(MessagesUtils.MSG_EMAIL_CONFLICT);
        });
    }
}
