package io.github.biblioteca.digital.api.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(Integer id, @NotBlank String name, @Email @NotBlank String email) {
}


