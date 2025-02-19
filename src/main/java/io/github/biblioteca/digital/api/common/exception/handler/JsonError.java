package io.github.biblioteca.digital.api.common.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonError {

    private Integer status;
    private OffsetDateTime timestamp;
    private String title;
    private String detail;
    private String userMessage;
    private List<Field> fields;

}
