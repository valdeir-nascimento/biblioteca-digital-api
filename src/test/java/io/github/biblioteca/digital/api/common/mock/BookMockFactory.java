package io.github.biblioteca.digital.api.common.mock;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.github.biblioteca.digital.api.infrastructure.model.Book;

import java.util.Collections;

public class BookMockFactory {

    private BookMockFactory() {
    }

    public static BookDTO getCreateBook() {
        return new BookDTO(null, "Jornada Java", "Robert Martin", true, 1);
    }

    public static BookDTO getBookSaved() {
        return new BookDTO(1, "Jornada Java", "Robert Martin", true, 1);
    }

    public static BookDTO getBookInvalid() {
        return new BookDTO(1, null, "Robert Martin", true, null);
    }

    public static Book getCreateBookModel() {
        Book bookModel = new Book();
        bookModel.setId(1L);
        bookModel.setTitle("Jornada Java");
        bookModel.setAuthor("Robert Martin");
        bookModel.setAvailable(true);
        return bookModel;
    }

    public static BookDTO getUpdateBook() {
        return new BookDTO(1, "Updated Title", "Updated Author", false, 20);
    }

    public static PageResponseDTO<BookDTO> getBookPageResponse() {
        final var bookPage = Collections.singletonList(getBookSaved());
        return PageResponseDTO.<BookDTO>builder()
                .content(bookPage)
                .page(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .build();
    }

}
