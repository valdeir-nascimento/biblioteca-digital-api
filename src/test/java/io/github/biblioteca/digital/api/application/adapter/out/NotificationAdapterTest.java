package io.github.biblioteca.digital.api.application.adapter.out;

import io.github.biblioteca.digital.api.common.dto.EmailDTO;
import io.github.biblioteca.digital.api.domain.port.in.EmailNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationAdapterTest {

    @Mock
    private MessagingNotificationUseCasePort messagingNotificationUseCasePort;

    @Mock
    private EmailNotificationUseCasePort emailNotificationUseCasePort;

    @InjectMocks
    private NotificationAdapter notificationAdapter;

    @Test
    public void givenEmailAndBookTitle_whenSendRentalConfirmation_thenEmailAndEventAreSent() {
        final var email = "user@example.com";
        final var bookTitle = "Effective Java";

        notificationAdapter.sendRentalConfirmation(email, bookTitle);

        ArgumentCaptor<EmailDTO> emailCaptor = ArgumentCaptor.forClass(EmailDTO.class);
        verify(emailNotificationUseCasePort).sendEmail(emailCaptor.capture());
        EmailDTO capturedEmail = emailCaptor.getValue();

        assertEquals(email, capturedEmail.to());
        assertEquals("Confirmação de aluguel de livro", capturedEmail.subject());
        assertEquals("Você alugou o livro: " + bookTitle, capturedEmail.body());

        ArgumentCaptor<String> eventCaptor = ArgumentCaptor.forClass(String.class);
        verify(messagingNotificationUseCasePort).sendEvent(eventCaptor.capture());
        String capturedEvent = eventCaptor.getValue();
        assertEquals("Aluguel de livro: " + bookTitle, capturedEvent);
    }

}