package io.github.biblioteca.digital.api.domain.port.in;

import io.github.biblioteca.digital.api.common.dto.EmailDTO;

public interface EmailNotificationUseCasePort {

    void sendEmail(EmailDTO emailDTO);

}
