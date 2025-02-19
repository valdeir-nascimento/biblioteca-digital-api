package io.github.biblioteca.digital.api.common.exception.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field {

    private String name;
    private String userMessage;

    private Field(String name, String userMessage) {
        this.name = name;
        this.userMessage = userMessage;
    }

    public static Field of(String name, String userMessage) {
        return new Field(name, userMessage);
    }

}
