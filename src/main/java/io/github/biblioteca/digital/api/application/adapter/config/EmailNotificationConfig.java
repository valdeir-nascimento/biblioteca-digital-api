package io.github.biblioteca.digital.api.application.adapter.config;

import io.github.biblioteca.digital.api.domain.port.in.EmailNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.usecase.EmailNotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailNotificationConfig {

    @Bean
    public EmailNotificationUseCasePort emailNotificationUseCase() {
        return new EmailNotificationUseCase();
    }

}
