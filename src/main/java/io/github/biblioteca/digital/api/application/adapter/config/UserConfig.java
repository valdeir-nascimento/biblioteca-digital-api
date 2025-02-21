package io.github.biblioteca.digital.api.application.adapter.config;

import io.github.biblioteca.digital.api.domain.port.in.UserRegisterUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.EmailValidatorUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.UserRepositoryPort;
import io.github.biblioteca.digital.api.domain.usecase.UserRegisterUseCase;
import io.github.biblioteca.digital.api.domain.usecase.EmailValidatorUseCase;
import io.github.biblioteca.digital.api.domain.usecase.UserValidationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserValidationUseCasePort userValidationUseCase(UserRepositoryPort userRepositoryPort) {
        return new UserValidationUseCase(userRepositoryPort);
    }

    @Bean
    public UserRegisterUseCasePort createUserUseCase(UserRepositoryPort userRepositoryPort, EmailValidatorUseCasePort emailValidatorUseCasePort) {
        return new UserRegisterUseCase(userRepositoryPort, emailValidatorUseCasePort);
    }

    @Bean
    public EmailValidatorUseCasePort emailValidatorUseCase(UserRepositoryPort userRepositoryPort) {
        return new EmailValidatorUseCase(userRepositoryPort);
    }

}
