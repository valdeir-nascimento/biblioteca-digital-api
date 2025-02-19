package io.github.biblioteca.digital.api.application.adapter.config;

import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;
import io.github.biblioteca.digital.api.domain.usecase.UserValidationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserValidationUseCase userValidationUseCase(UserRepositoryPort userRepositoryPort) {
        return new UserValidationUseCase(userRepositoryPort);
    }

}
