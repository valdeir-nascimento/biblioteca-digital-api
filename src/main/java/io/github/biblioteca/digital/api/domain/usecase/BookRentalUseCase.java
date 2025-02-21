package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.BookRentalDTO;
import io.github.biblioteca.digital.api.common.mapper.BookMapper;
import io.github.biblioteca.digital.api.domain.port.in.BookRentalUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.NotificationPort;
import io.github.biblioteca.digital.api.infrastructure.model.Book;

public class BookRentalUseCase implements BookRentalUseCasePort {

    private final BookUseCasePort bookUseCasePort;
    private final BookMapper bookMapper;
    private final NotificationPort notificationPort;

    public BookRentalUseCase(BookUseCasePort bookUseCasePort, BookMapper bookMapper, NotificationPort notificationPort) {
        this.bookUseCasePort = bookUseCasePort;
        this.bookMapper = bookMapper;
        this.notificationPort = notificationPort;
    }

    @Override
    public BookDTO rentBook(Integer bookId, BookRentalDTO bookRentalDTO) {
        BookDTO bookDTO = bookUseCasePort.findById(bookId);
        Book book = bookMapper.toEntity(bookDTO);
        book.rent();
        bookDTO = bookUseCasePort.update(bookId, bookMapper.toDTO(book));
        notificationPort.sendRentalConfirmation(bookRentalDTO.email(), bookDTO.title());
        return bookDTO;
    }

}
