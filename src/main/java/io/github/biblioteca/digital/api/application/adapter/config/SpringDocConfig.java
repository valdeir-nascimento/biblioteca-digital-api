package io.github.biblioteca.digital.api.application.adapter.config;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.Map;
import io.github.biblioteca.digital.api.common.exception.handler.JsonError;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.Getter;

@Getter
@Configuration
public class SpringDocConfig {

    private static final String TITULO_OPENAPI = "Book Rental Management";
    private static final String VERSAO_OPENAPI = "v1";
    private static final String DESCRICAO_OPENAPI = "REST API for Book Rental Management";
    private static final String LICENCE_NAME_OPENAPI = "Apache 2.0";
    private static final String LICENSE_NAME_URL_OPENAPI = "http://springdoc.com";
    private static final String UREL_SEF = "https://www.snowmanlabs.com";
    private static final String BAD_REQUEST_RESPONSE = "BadRequestResponse";
    private static final String NOT_FOUND_RESPONSE = "NotFoundResponse";
    private static final String NOT_ACCEPTABLE_RESPONSE = "NotAcceptableResponse";
    private static final String INTERNAL_SERVER_ERROR_RESPONSE = "InternalServerErrorResponse";
    private static final String DESCRICAO_BAD_REQUEST_RESPONSE = "Requisição inválida";
    private static final String DESCRICAO_NOT_FOUND_RESPONSE = "Recurso não encontrado";
    private static final String DESCRICAO_NOT_ACCEPTABLE_RESPONSE = "Recurso não possui representação que poderia ser aceita pelo consumidor";
    private static final String DESCRICAO_INTERNAL_SERVER_ERROR_RESPONSE = "Erro interno no servidor";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITULO_OPENAPI)
                        .version(VERSAO_OPENAPI)
                        .description(DESCRICAO_OPENAPI)
                        .license(new License()
                                .name(LICENCE_NAME_OPENAPI)
                                .url(LICENSE_NAME_URL_OPENAPI)
                        )
                ).externalDocs(new ExternalDocumentation()
                        .url(UREL_SEF)
                ).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses()
                        )
                );
    }

    @Bean
    public OpenApiCustomizer openApiCustomiser() {
        return openApi ->
                openApi.getPaths()
                        .values()
                        .forEach(pathItem -> pathItem.readOperationsMap()
                                .forEach((httpMethod, operation) -> {
                                    ApiResponses responses = operation.getResponses();
                                    switch (httpMethod) {
                                        case GET:
                                            responses.addApiResponse("406", new ApiResponse().$ref(NOT_ACCEPTABLE_RESPONSE));
                                            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
                                            break;
                                        case POST, PUT:
                                            responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
                                            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
                                            break;
                                        default:
                                            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
                                            break;
                                    }
                                })
                        );
    }


    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();
        Map<String, Schema> jsonErrorMap = ModelConverters.getInstance().read(JsonError.class);
        Map<String, Schema> jsonErrorFieldMap = ModelConverters.getInstance().read(JsonError.Field.class);
        schemaMap.putAll(jsonErrorMap);
        schemaMap.putAll(jsonErrorFieldMap);
        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<JsonError>().$ref("JsonError")));

        apiResponseMap.put(BAD_REQUEST_RESPONSE, new ApiResponse()
                .description(DESCRICAO_BAD_REQUEST_RESPONSE)
                .content(content));

        apiResponseMap.put(NOT_FOUND_RESPONSE, new ApiResponse()
                .description(DESCRICAO_NOT_FOUND_RESPONSE)
                .content(content));

        apiResponseMap.put(NOT_ACCEPTABLE_RESPONSE, new ApiResponse()
                .description(DESCRICAO_NOT_ACCEPTABLE_RESPONSE)
                .content(content));

        apiResponseMap.put(INTERNAL_SERVER_ERROR_RESPONSE, new ApiResponse()
                .description(DESCRICAO_INTERNAL_SERVER_ERROR_RESPONSE)
                .content(content));

        return apiResponseMap;
    }
}






