package io.github.biblioteca.digital.api.common.dto;

public record BookDTO(Integer id, String title, String author, Boolean available, Integer userId) {
}
