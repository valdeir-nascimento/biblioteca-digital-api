package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.exception.BookNotAvailableException;
import io.github.biblioteca.digital.api.common.mapper.BookMapper;
import io.github.biblioteca.digital.api.common.mock.BookMockFactory;
import io.github.biblioteca.digital.api.common.util.MessagesUtils;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.NotificationPort;
import io.github.biblioteca.digital.api.infrastructure.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRentalUseCaseTest {

    @Mock
    private BookUseCasePort bookUseCasePort;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private NotificationPort notificationPort;

    @InjectMocks
    private BookRentalUseCase bookRentalUseCase;

    @Test
    void givenAvailableBook_whenRentBook_thenReturnUpdatedBookDTO() {
        final var bookId = 1;
        final var bookRentalDTO = BookMockFactory.getBookRental();
        final var originalBookDTO = BookMockFactory.getBookSaved();
        final var book = BookMockFactory.getBook();
        final var updatedBookDTO = BookMockFactory.getUpdateBook();

        when(bookUseCasePort.findById(bookId)).thenReturn(originalBookDTO);
        when(bookMapper.toEntity(originalBookDTO)).thenReturn(book);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(updatedBookDTO);
        when(bookUseCasePort.update(eq(bookId), any(BookDTO.class))).thenReturn(updatedBookDTO);

        BookDTO result = bookRentalUseCase.rentBook(bookId, bookRentalDTO);

        assertNotNull(result);
        assertFalse(result.available());
        verify(notificationPort).sendRentalConfirmation(bookRentalDTO.email(), updatedBookDTO.title());
    }

    @Test
    void givenUnavailableBook_whenRentBook_thenThrowBookNotAvailableException() throws Exception {
        final var bookId = 2;
        final var bookRentalDTO = BookMockFactory.getBookRental();
        final var originalBookDTO = BookMockFactory.getBookSaved();
        final var book = BookMockFactory.getBook();
        book.setAvailable(false);
        book.setRentalDate(LocalDateTime.now());

        when(bookUseCasePort.findById(bookId)).thenReturn(originalBookDTO);
        when(bookMapper.toEntity(originalBookDTO)).thenReturn(book);

        BookNotAvailableException exception = assertThrows(BookNotAvailableException.class, () -> bookRentalUseCase.rentBook(bookId, bookRentalDTO));
        assertEquals(MessagesUtils.MSG_BOOK_NOT_AVAILABLE, exception.getMessage());
        verify(notificationPort, never()).sendRentalConfirmation(anyString(), anyString());
    }
}