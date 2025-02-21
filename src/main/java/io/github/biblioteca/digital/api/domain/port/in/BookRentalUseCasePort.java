package io.github.biblioteca.digital.api.domain.port.in;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.BookRentalDTO;

public interface BookRentalUseCasePort {

    BookDTO rentBook(Integer bookId, BookRentalDTO bookRentalDTO);

}
