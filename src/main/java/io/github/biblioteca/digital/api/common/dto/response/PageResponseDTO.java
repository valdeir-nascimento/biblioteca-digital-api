package io.github.biblioteca.digital.api.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageResponseDTO<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}