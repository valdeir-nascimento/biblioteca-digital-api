package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.BookRepositoryPort;
import io.github.biblioteca.digital.api.mock.BookMockFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookUseCaseTest {

    @Mock
    private BookRepositoryPort bookRepositoryPort;

    @Mock
    private UserValidationUseCasePort userValidationUseCasePort;

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
        assertEquals(bookSaved.tiltle(), result.tiltle());
        assertEquals(bookSaved.author(), result.author());
        assertEquals(bookSaved.available(), result.available());
        assertEquals(bookSaved.userId(), result.userId());

        verify(userValidationUseCasePort, times(1)).validateUserExists(book.userId());
        verify(bookRepositoryPort, times(1)).create(book);
    }

    @Test
    void givenInvalidUser_whenInvokeCreateBook_thenThrowsException() {
        final var book = BookMockFactory.getCreateBook();

        doThrow(new IllegalArgumentException("User not found")).when(userValidationUseCasePort).validateUserExists(book.userId());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bookUseCase.create(book));

        assertEquals("User not found", exception.getMessage());
        verify(userValidationUseCasePort, times(1)).validateUserExists(book.userId());
        verify(bookRepositoryPort, never()).create(any());
    }

    @Test
    void givenValidUser_whenUpdateBook_thenRepositoryUpdateCalled() {
        final var bookId = 1;
        final var bookDTO = BookMockFactory.getBookSaved();

        bookUseCase.update(bookId, bookDTO);

        verify(userValidationUseCasePort, times(1)).validateUserExists(bookDTO.userId());
        verify(bookRepositoryPort, times(1)).update(bookId, bookDTO);
    }

    @Test
    void givenExistingBook_whenFindById_thenReturnsBook() {
        Integer bookId = 1;
        final var expectedBook = BookMockFactory.getBookSaved();

        when(bookRepositoryPort.findById(bookId)).thenReturn(expectedBook);

        final var result = bookUseCase.findById(bookId);

        assertEquals(expectedBook, result);
        verify(bookRepositoryPort, times(1)).findById(bookId);
    }

    @Test
    void givenValidBookId_whenDeleteById_thenRepositoryDeleteCalled() {
        Integer bookId = 1;

        bookUseCase.deleteById(bookId);

        verify(bookRepositoryPort, times(1)).deleteById(bookId);
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
    }
}