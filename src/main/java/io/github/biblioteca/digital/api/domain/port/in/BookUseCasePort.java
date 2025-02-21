package io.github.biblioteca.digital.api.domain.port.in;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;

public interface BookUseCasePort {

    BookDTO create(BookDTO bookDTO);

    BookDTO update(Integer bookId, BookDTO bookDTO);

    BookDTO findById(Integer bookId);

    void deleteById(Integer bookId);

    PageResponseDTO<BookDTO> findAll(int page, int size);

}
