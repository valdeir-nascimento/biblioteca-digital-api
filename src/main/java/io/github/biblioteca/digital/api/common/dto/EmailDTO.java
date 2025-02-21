package io.github.biblioteca.digital.api.common.dto;

public record EmailDTO(String to, String subject, String body) {
    public static EmailDTO of(String to, String subject, String body) {
        return new EmailDTO(to, subject, body);
    }
}
