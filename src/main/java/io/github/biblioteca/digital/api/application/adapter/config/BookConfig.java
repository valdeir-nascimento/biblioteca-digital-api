package io.github.biblioteca.digital.api.application.adapter.config;

import io.github.biblioteca.digital.api.common.mapper.BookMapper;
import io.github.biblioteca.digital.api.domain.port.in.BookRentalUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.BookRepositoryPort;
import io.github.biblioteca.digital.api.domain.port.out.NotificationPort;
import io.github.biblioteca.digital.api.domain.usecase.BookRentalUseCase;
import io.github.biblioteca.digital.api.domain.usecase.BookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {

    @Bean
    public BookUseCasePort bookUseCase(BookRepositoryPort bookRepositoryPort, UserValidationUseCasePort validationUseCasePort, MessagingNotificationUseCasePort messagingNotificationUseCasePort) {
        return new BookUseCase(bookRepositoryPort, validationUseCasePort, messagingNotificationUseCasePort);
    }

    @Bean
    public BookRentalUseCasePort bookRentalUseCase(BookUseCasePort bookUseCasePort, BookMapper bookMapper, NotificationPort notificationPort) {
        return new BookRentalUseCase(bookUseCasePort, bookMapper, notificationPort);
    }

}
