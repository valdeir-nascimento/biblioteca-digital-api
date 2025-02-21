package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.EmailDTO;
import io.github.biblioteca.digital.api.domain.port.in.EmailNotificationUseCasePort;

import static java.lang.String.*;

public class EmailNotificationUseCase implements EmailNotificationUseCasePort {
    @Override
    public void sendEmail(EmailDTO emailDTO) {
        System.out.println(format("Enviando email para %s com assunto: %s e corpo: %s", emailDTO.to(), emailDTO.subject(), emailDTO.body()));
    }
}
