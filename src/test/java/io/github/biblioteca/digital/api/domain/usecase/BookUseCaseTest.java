package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.exception.NotFoundException;
import io.github.biblioteca.digital.api.domain.port.in.MessagingNotificationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.BookRepositoryPort;
import io.github.biblioteca.digital.api.common.mock.BookMockFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.github.biblioteca.digital.api.common.util.MessagesUtils.MSG_USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookUseCaseTest {

    @Mock
    private BookRepositoryPort bookRepositoryPort;

    @Mock
    private UserValidationUseCasePort userValidationUseCasePort;

    @Mock
    private MessagingNotificationUseCasePort messagingNotificationUseCasePort;

    @InjectMocks
    private BookUseCase bookUseCase;

    @Test
    void givenNewBookData_whenInvokeCreateBook_thenNewBookIsCreated() {
        final var book = BookMockFactory.getCreateBook();
        final var bookSaved = BookMockFactory.getBookSaved();

        when(bookRepositoryPort.create(book)).thenReturn(bookSaved);

        final var result = bookUseCase.create(book);

        assertNotNull(result);
        assertEquals(bookSaved.id(), result.id());
        assertEquals(bookSaved.title(), result.title());
        assertEquals(bookSaved.author(), result.author());
        assertEquals(bookSaved.available(), result.available());
        assertEquals(bookSaved.userId(), result.userId());

        verify(bookRepositoryPort, times(1)).create(book);
        verify(userValidationUseCasePort, never()).validateUserExists(any());
        verify(messagingNotificationUseCasePort, times(1)).sendEvent(anyString());
    }

    @Test
    void givenInvalidUser_whenInvokeCreateBook_thenThrowsException() {
        final var book = BookMockFactory.getBookInvalid();

        doThrow(new NotFoundException(MSG_USER_NOT_FOUND)).when(userValidationUseCasePort)
                .validateUserExists(book.userId());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookUseCase.create(book));
        assertEquals(MSG_USER_NOT_FOUND, exception.getMessage());
        verify(bookRepositoryPort, never()).create(book);
        verify(messagingNotificationUseCasePort, never()).sendEvent(anyString());
    }

    @Test
    void givenValidUser_whenUpdateBook_thenRepositoryUpdateCalled() {
        final var bookId = 1;
        final var bookDTO = BookMockFactory.getBookSaved();

        bookUseCase.update(bookId, bookDTO);

        verify(userValidationUseCasePort, times(1)).validateUserExists(bookDTO.userId());
        verify(bookRepositoryPort, times(1)).update(bookId, bookDTO);
        verify(messagingNotificationUseCasePort, times(1)).sendEvent(anyString());
    }

    @Test
    void givenExistingBook_whenFindById_thenReturnsBook() {
        Integer bookId = 1;
        final var expectedBook = BookMockFactory.getBookSaved();

        when(bookRepositoryPort.findById(bookId)).thenReturn(expectedBook);

        final var result = bookUseCase.findById(bookId);

        assertEquals(expectedBook, result);
        verify(bookRepositoryPort, times(1)).findById(bookId);
        verify(messagingNotificationUseCasePort, times(1)).sendEvent(anyString());
    }

    @Test
    void givenValidBookId_whenDeleteById_thenRepositoryDeleteCalled() {
        Integer bookId = 1;

        bookUseCase.deleteById(bookId);

        verify(bookRepositoryPort, times(1)).deleteById(bookId);
        verify(messagingNotificationUseCasePort, times(1)).sendEvent(anyString());
    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturnsPageResponse() {
        int page = 0;
        int size = 10;
        final var expectedResponse = BookMockFactory.getBookPageResponse();
        when(bookRepositoryPort.findAll(page, size)).thenReturn(expectedResponse);

        final var result = bookUseCase.findAll(page, size);

        assertEquals(expectedResponse, result);
        verify(bookRepositoryPort, times(1)).findAll(page, size);
        verify(messagingNotificationUseCasePort, times(1)).sendEvent(anyString());
    }
}