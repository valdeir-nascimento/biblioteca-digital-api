package io.github.biblioteca.digital.api.application.out;

import io.github.biblioteca.digital.api.application.adapter.out.BookRepositoryAdapter;
import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.github.biblioteca.digital.api.common.exception.NotFoundException;
import io.github.biblioteca.digital.api.common.mapper.BookMapper;
import io.github.biblioteca.digital.api.infrastructure.model.Book;
import io.github.biblioteca.digital.api.infrastructure.repository.BookRepository;
import io.github.biblioteca.digital.api.mock.BookMockFactory;
import io.github.biblioteca.digital.api.mock.UserMockFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryAdapterTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookRepositoryAdapter bookRepositoryAdapter;

    @Test
    void givenValidBookDTO_whenCreate_thenReturnCreatedBookDTO() {
        final var inputDto = BookMockFactory.getCreateBook();
        final var bookEntity = BookMockFactory.getCreateBookModel();
        final var outputDto = BookMockFactory.getBookSaved();

        when(bookMapper.toEntity(inputDto)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookMapper.toDTO(bookEntity)).thenReturn(outputDto);

        BookDTO result = bookRepositoryAdapter.create(inputDto);

        assertNotNull(result);
        assertEquals(outputDto, result);
        verify(bookMapper).toEntity(inputDto);
        verify(bookRepository).save(bookEntity);
        verify(bookMapper).toDTO(bookEntity);
    }

    @Test
    void givenExistingBook_whenUpdate_thenUpdateAndSaveBook() {
        final var bookId = 1;
        final var updateDto = BookMockFactory.getUpdateBook();
        final var existingBook = BookMockFactory.getCreateBookModel();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        doNothing().when(bookMapper).copyToProperties(updateDto, existingBook);
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        assertDoesNotThrow(() -> bookRepositoryAdapter.update(bookId, updateDto));

        verify(bookRepository).findById(bookId);
        verify(bookMapper).copyToProperties(updateDto, existingBook);
        verify(bookRepository).save(existingBook);
    }

    @Test
    void givenExistingBook_whenFindById_thenReturnBookDTO() {
        final var bookId = 1;
        final var expectedDto = BookMockFactory.getBookSaved();
        final var existingBook = BookMockFactory.getCreateBookModel();
        final var user = UserMockFactory.getUserModel();
        existingBook.setUser(user);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookMapper.toDTO(existingBook)).thenReturn(expectedDto);

        BookDTO result = bookRepositoryAdapter.findById(bookId);

        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(bookRepository).findById(bookId);
        verify(bookMapper).toDTO(existingBook);
    }

    @Test
    void givenExistingBook_whenDeleteById_thenDeleteBookSuccessfully() {
        final var bookId = 1;
        doNothing().when(bookRepository).deleteById(bookId);
        assertDoesNotThrow(() -> bookRepositoryAdapter.deleteById(bookId));
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void givenNonExistingBook_whenDeleteById_thenThrowNotFoundException() {
        final var bookId = 999;
        doThrow(new EmptyResultDataAccessException(1)).when(bookRepository).deleteById(bookId);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookRepositoryAdapter.deleteById(bookId);
        });
        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void givenPageable_whenFindAll_thenReturnPageResponseDTO() {
        int page = 0;
        int size = 1;
        final var pageRequest = PageRequest.of(page, size);
        final var book = BookMockFactory.getCreateBookModel();
        final var user = UserMockFactory.getUserModel();
        book.setUser(user);

        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books, pageRequest, books.size());

        BookDTO expectedBookDTO = BookMockFactory.getBookSaved();

        when(bookRepository.findAll(pageRequest)).thenReturn(bookPage);
        when(bookMapper.toDTO(book)).thenReturn(expectedBookDTO);

        PageResponseDTO<BookDTO> response = bookRepositoryAdapter.findAll(page, size);

        assertNotNull(response);
        assertEquals(page, response.getPage());
        assertEquals(size, response.getSize());
        assertEquals(books.size(), response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getContent().size());
        assertTrue(response.getContent().containsAll(List.of(expectedBookDTO)));
        verify(bookRepository).findAll(pageRequest);
        verify(bookMapper).toDTO(book);
    }
}
