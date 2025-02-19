package io.github.biblioteca.digital.api.common.exception.handler;

import lombok.Getter;

@Getter
public enum TypeError {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado"),
    ERRO_NEGOCIO("Violação de regra de negócio"),
    PARAMETRO_INVALIDO("Parâmetro inválido"),
    DADOS_INVALIDOS("Dados inválidos"),
    ERRO_DE_SISTEMA("Erro de sistema");

    private String title;

    TypeError(String title) {
        this.title = title;
    }

}

