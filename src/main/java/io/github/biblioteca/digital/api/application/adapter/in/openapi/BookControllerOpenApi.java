package io.github.biblioteca.digital.api.application.adapter.in.openapi;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.common.dto.BookRentalDTO;
import io.github.biblioteca.digital.api.common.dto.response.PageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Books", description = "Endpoints for managing books and rentals")
public interface BookControllerOpenApi {

    @Operation(summary = "Create a new book", description = "Creates a new book record in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book created successfully", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    ResponseEntity<BookDTO> create(BookDTO bookDTO);

    @Operation(summary = "Update an existing book", description = "Updates the book details based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book updated successfully", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    ResponseEntity<BookDTO> update(@Parameter(description = "ID of the book to update", example = "1") Integer bookId, BookDTO bookDTO);

    @Operation(summary = "Find a book by ID", description = "Retrieves a single book by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book found", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    ResponseEntity<BookDTO> findById(@Parameter(description = "ID of the book", example = "1") Integer bookId);

    @Operation(summary = "Delete a book", description = "Deletes a book by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Book deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    ResponseEntity<Void> deleteById(@Parameter(description = "ID of the book to delete", example = "1") Integer bookId);

    @Operation(summary = "List all books", description = "Returns a paginated list of books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Books retrieved successfully", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponseDTO.class)))
    })
    ResponseEntity<PageResponseDTO<BookDTO>> findAll(
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size);

    @Operation(summary = "Rent a book", description = "Rents a book using its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book rented successfully", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "400", description = "Book not available for rental", content = @Content)
    })
    ResponseEntity<BookDTO> rentBook(@Parameter(description = "ID of the book to rent", example = "1") Integer bookId, BookRentalDTO bookRentalDTO);
}
