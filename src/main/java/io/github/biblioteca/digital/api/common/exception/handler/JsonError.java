package io.github.biblioteca.digital.api.common.exception.handler;

import java.time.OffsetDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "JsonError")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class JsonError {

	@Schema(description= "300, 400, 500")
	private Integer status;

	@Schema(example = "2023-09-25T08:09:02.70844Z")
	private OffsetDateTime timestamp;

	@Schema(example = "https://www.fazenda.mg.gov.br/dados-invalidos")
	private String type;

	@Schema(example = "Titulo da mensagem de excecao")
	private String title;

	@Schema(example = "Detalhamento da mensagem de excecao")
	private String detail;

	@Schema(example = "Mensagem explicativa para o usuario")
	private String userMessage;

	@Schema(example = "Lista de objetos ou campos que geraram o erro (opcional)")
	private List<Field> fields;

	@Schema(example = "Field")
	@Getter
	@Builder
	public static class Field {

		@Schema(example = "Nome")
		private String name;

		@Schema(example = "Nome Invalido")
		private String userMessage;

	}

}


