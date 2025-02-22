package io.github.biblioteca.digital.api.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookDTO(
    Integer id,
    @NotBlank String title,
    @NotBlank String author,
    @NotNull Boolean available,
    Integer userId,
    LocalDateTime rentalDate
) { }
