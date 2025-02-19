package io.github.biblioteca.digital.api.application.adapter.in.controller;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.github.biblioteca.digital.api.domain.port.in.BookUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookUseCasePort bookUseCasePort;

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        final var response = bookUseCasePort.create(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> update(@PathVariable Integer bookId, @RequestBody BookDTO bookDTO) {
        bookUseCasePort.update(bookId, bookDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> findById(@PathVariable Integer bookId) {
        final var response = bookUseCasePort.findById(bookId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping( "/{bookId}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer bookId) {
        bookUseCasePort.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<BookDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        final var response = bookUseCasePort.findAll(page, size);
        return ResponseEntity.ok().body(response);
    }

}
