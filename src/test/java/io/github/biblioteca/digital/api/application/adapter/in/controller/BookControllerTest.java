package io.github.biblioteca.digital.api.application.adapter.in.controller;

import io.github.biblioteca.digital.api.common.builder.UrlBuilder;
import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.util.JsonUtils;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import io.github.biblioteca.digital.api.common.mock.BookMockFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private static final String PATH = "/books";

    @InjectMocks
    private BookController bookController;
    @Mock
    private BookUseCasePort bookUseCasePort;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    @SneakyThrows
    void givenValidBookDTO_whenCreate_thenReturnStatusCreated() {
        final var url = UrlBuilder.builder(PATH).build();
        final var jsonBookCreatedSuccess = JsonUtils.getContentFromResource("/json/book-created-success.json");
        final var expectedResponse = BookMockFactory.getBookSaved();
        when(bookUseCasePort.create(any(BookDTO.class))).thenReturn(expectedResponse);
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookCreatedSuccess))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void givenValidBookId_whenFindById_thenReturnsBook() {
        final var bookId = 1;
        final var url = UrlBuilder.builder(PATH).pathVariable(bookId).build();
        final var expectedResponse = BookMockFactory.getBookSaved();
        when(bookUseCasePort.findById(bookId)).thenReturn(expectedResponse);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponse.id()))
                .andExpect(jsonPath("$.title").value(expectedResponse.title()))
                .andExpect(jsonPath("$.author").value(expectedResponse.author()))
                .andExpect(jsonPath("$.available").value(expectedResponse.available()))
                .andExpect(jsonPath("$.userId").value(expectedResponse.userId()));
    }

    @Test
    @SneakyThrows
    void givenValidBookIdAndBookDTO_whenUpdateBook_thenReturnsNoContent() {
        final var bookId = 1;
        final var url = UrlBuilder.builder(PATH).pathVariable(bookId).build();
        final var jsonBookUpdateSuccess = JsonUtils.getContentFromResource("/json/book-update-success.json");
        doNothing().when(bookUseCasePort).update(eq(bookId), any(BookDTO.class));
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookUpdateSuccess))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void givenValidBookId_whenDeleteBook_thenReturnsNoContent() {
        final var bookId = 1;
        final var url = UrlBuilder.builder(PATH).pathVariable(bookId).build();
        doNothing().when(bookUseCasePort).deleteById(bookId);
        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void givenPageAndSize_whenFindAll_thenReturnsPageResponse() {
        final var url = UrlBuilder.builder(PATH)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .build();
        final var pageResponse = BookMockFactory.getBookPageResponse();
        when(bookUseCasePort.findAll(0, 10)).thenReturn(pageResponse);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Jornada Java"))
                .andExpect(jsonPath("$.content[0].author").value("Robert Martin"))
                .andExpect(jsonPath("$.content[0].available").value(true))
                .andExpect(jsonPath("$.content[0].userId").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    @SneakyThrows
    void givenNonExistingBookId_whenFindById_thenReturnsNotFound() {
        final var bookId = 999;
        final var url = UrlBuilder.builder(PATH).pathVariable(bookId).build();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(bookUseCasePort)
                .findById(bookId);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void givenInvalidBookDTO_whenCreateBook_thenReturnsBadRequest() {
        final var url = UrlBuilder.builder(PATH).build();
        final var jsonBookCretedParamInvalid = JsonUtils.getContentFromResource("/json/book-created-param-invalid.json");
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookCretedParamInvalid))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void givenInvalidBookDTO_whenUpdateBook_thenReturnsBadRequest() {
        final var bookId = 1;
        final var url = UrlBuilder.builder(PATH).pathVariable(bookId).build();
        final var jsonBookUpdateParamInvalid = JsonUtils.getContentFromResource("/json/book-update-param-invalid.json");
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookUpdateParamInvalid))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenInvalidBookId_whenDeleteById_thenThrowNotFoundException() {
        final var bookId = 999;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(bookUseCasePort)
                .deleteById(bookId);
        assertThrows(ResponseStatusException.class, () -> bookController.deleteById(bookId));
    }
}