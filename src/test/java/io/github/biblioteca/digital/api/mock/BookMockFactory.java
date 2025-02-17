package io.github.biblioteca.digital.api.mock;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;

import java.util.Collections;

public class BookMockFactory {

    private BookMockFactory() {
    }

    public static BookDTO getCreateBook() {
        return new BookDTO(null, "Jornada Java", "Aprenda a programar em Java", true, 1);
    }

    public static BookDTO getBookSaved() {
        return new BookDTO(1, "Jornada Java", "Aprenda a programar em Java", true, 1);
    }

    public static PageResponseDTO<BookDTO> getBookPageResponse() {
        final var bookPage = Collections.singletonList(getBookSaved());
        return PageResponseDTO.<BookDTO>builder()
                .content(bookPage)
                .page(1)
                .size(1)
                .totalElements(0)
                .totalPages(0)
                .build();
    }

}
