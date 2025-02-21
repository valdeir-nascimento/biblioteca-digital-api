package io.github.biblioteca.digital.api.domain.port.out;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;

public interface BookRepositoryPort {

    BookDTO create(BookDTO bookDTO);

    BookDTO update(Integer bookId, BookDTO dto);

    BookDTO findById(Integer bookId);

    void deleteById(Integer bookId);

    PageResponseDTO<BookDTO> findAll(int page, int size);

}
