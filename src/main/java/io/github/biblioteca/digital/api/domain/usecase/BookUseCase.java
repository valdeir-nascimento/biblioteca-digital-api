package io.github.biblioteca.digital.api.domain.usecase;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import io.github.biblioteca.digital.api.domain.port.in.UserValidationUseCasePort;
import io.github.biblioteca.digital.api.domain.port.out.BookRepositoryPort;

import java.util.Objects;

public class BookUseCase implements BookUseCasePort {

    private final BookRepositoryPort bookRepositoryPort;

    private final UserValidationUseCasePort validationUseCasePort;

    public BookUseCase(BookRepositoryPort bookRepositoryPort, UserValidationUseCasePort validationUseCasePort) {
        this.bookRepositoryPort = bookRepositoryPort;
        this.validationUseCasePort = validationUseCasePort;
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        if (bookDTO.id() != null) {
            validationUseCasePort.validateUserExists(bookDTO.userId());
        }
        return bookRepositoryPort.create(bookDTO);
    }

    @Override
    public BookDTO update(Integer bookId, BookDTO bookDTO) {
        validationUseCasePort.validateUserExists(bookDTO.userId());
        return bookRepositoryPort.update(bookId, bookDTO);
    }

    @Override
    public BookDTO findById(Integer bookId) {
        return bookRepositoryPort.findById(bookId);
    }

    @Override
    public void deleteById(Integer bookId) {
        bookRepositoryPort.deleteById(bookId);
    }

    @Override
    public PageResponseDTO<BookDTO> findAll(int page, int size) {
        return bookRepositoryPort.findAll(page, size);
    }
}
