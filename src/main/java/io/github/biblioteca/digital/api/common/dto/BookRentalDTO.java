package io.github.biblioteca.digital.api.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BookRentalDTO(@NotBlank @Email String email) {
}
