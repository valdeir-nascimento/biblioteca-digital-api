package io.github.biblioteca.digital.api.application.adapter.out;

import io.github.biblioteca.digital.api.common.dto.EmailDTO;
import io.github.biblioteca.digital.api.domain.port.in.EmailNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {

    private final MessagingNotificationUseCasePort messagingNotificationUseCasePort;
    private final EmailNotificationUseCasePort emailNotificationUseCasePort;

    @Override
    public void sendRentalConfirmation(String email, String bookTitle) {
        final var subject = "Confirmação de aluguel de livro";
        final var body = "Você alugou o livro: " + bookTitle;
        emailNotificationUseCasePort.sendEmail(EmailDTO.of(email, subject, body));

        final var event = "Aluguel de livro: " + bookTitle;
        messagingNotificationUseCasePort.sendEvent(event);
    }
}
