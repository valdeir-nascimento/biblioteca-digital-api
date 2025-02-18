package io.github.biblioteca.digital.api.application.adapter.out;

import io.github.biblioteca.digital.api.common.mapper.BookMapper;
import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.github.biblioteca.digital.api.common.exception.NotFoundException;
import io.github.biblioteca.digital.api.domain.port.out.BookRepositoryPort;
import io.github.biblioteca.digital.api.infrastructure.model.Book;
import io.github.biblioteca.digital.api.infrastructure.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepositoryPort {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDTO create(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toDTO(book);
    }

    @Override
    public void update(Integer bookId, BookDTO bookDTO) {
        Book book = getBook(bookId);
        bookMapper.copyToProperties(bookDTO, book);
        bookRepository.save(book);
    }

    @Override
    public BookDTO findById(Integer bookId) {
        Book book = getBook(bookId);
        return bookMapper.toDTO(book);
    }

    @Override
    public void deleteById(Integer bookId) {
        try {
            bookRepository.deleteById(bookId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Book not found");
        }
    }

    @Override
    public PageResponseDTO<BookDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageRequest);
        List<BookDTO> bookList = bookPage.stream()
                        .map(bookMapper::toDTO)
                        .toList();
        return PageResponseDTO.<BookDTO>builder()
                .content(bookList)
                .page(page)
                .size(size)
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .build();
    }

    private Book getBook(Integer bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }
}
