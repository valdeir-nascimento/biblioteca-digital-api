package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.BookRepositoryPort;

public class BookUseCase implements BookUseCasePort {

    private final BookRepositoryPort bookRepositoryPort;
    private final UserValidationUseCasePort validationUseCasePort;
    private final MessagingNotificationUseCasePort messagingNotificationUseCasePort;

    public BookUseCase(BookRepositoryPort bookRepositoryPort, UserValidationUseCasePort validationUseCasePort, MessagingNotificationUseCasePort messagingNotificationUseCasePort) {
        this.bookRepositoryPort = bookRepositoryPort;
        this.validationUseCasePort = validationUseCasePort;
        this.messagingNotificationUseCasePort = messagingNotificationUseCasePort;
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        if (bookDTO.id() != null) {
            validationUseCasePort.validateUserExists(bookDTO.userId());
        }
        messagingNotificationUseCasePort.sendEvent("Book created: " + bookDTO.title());
        return bookRepositoryPort.create(bookDTO);
    }

    @Override
    public BookDTO update(Integer bookId, BookDTO bookDTO) {
        validationUseCasePort.validateUserExists(bookDTO.userId());
        messagingNotificationUseCasePort.sendEvent("Book updated: " + bookDTO.title());
        return bookRepositoryPort.update(bookId, bookDTO);
    }

    @Override
    public BookDTO findById(Integer bookId) {
        messagingNotificationUseCasePort.sendEvent("Book found: " + bookId);
        return bookRepositoryPort.findById(bookId);
    }

    @Override
    public void deleteById(Integer bookId) {
        bookRepositoryPort.deleteById(bookId);
        messagingNotificationUseCasePort.sendEvent("Book deleted: " + bookId);
    }

    @Override
    public PageResponseDTO<BookDTO> findAll(int page, int size) {
        messagingNotificationUseCasePort.sendEvent("Book list found");
        return bookRepositoryPort.findAll(page, size);
    }
}
