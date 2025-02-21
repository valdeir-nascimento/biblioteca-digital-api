package io.github.biblioteca.digital.api.common.exception.handler;

import lombok.Getter;

@Getter
public enum TypeError {

    DADOS_INVALIDOS("/dados-invalidos", "Dados invalidos"),
    ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel,"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso nao encontrado"),
    ERRO_NEGOCIO("/erro-negocio", "Violacao de regra de negocio");

    private String title;
    private String uri;

    TypeError(String path, String title) {
        this.uri = "https://www.snowmanlabs.com" + path;
        this.title = title;
    }

}