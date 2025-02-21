package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;

public class MessagingNotificationUseCase implements MessagingNotificationUseCasePort {

    @Override
    public void sendEvent(String event) {
        System.out.println("Enviando evento de mensageria: " + event);
    }
}
