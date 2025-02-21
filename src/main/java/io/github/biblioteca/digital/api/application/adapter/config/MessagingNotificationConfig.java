package io.github.biblioteca.digital.api.application.adapter.config;

import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.usecase.MessagingNotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingNotificationConfig {

    @Bean
    public MessagingNotificationUseCasePort messagingNotificationUseCasePort() {
        return new MessagingNotificationUseCase();
    }

}
