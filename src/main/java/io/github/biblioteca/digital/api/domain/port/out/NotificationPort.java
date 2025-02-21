package io.github.biblioteca.digital.api.domain.port.out;

public interface NotificationPort {

    void sendRentalConfirmation(String email, String bookTitle);

}
