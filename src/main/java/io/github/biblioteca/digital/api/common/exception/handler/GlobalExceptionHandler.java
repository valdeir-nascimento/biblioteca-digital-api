package io.github.biblioteca.digital.api.common.exception.handler;


import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import io.github.biblioteca.digital.api.common.exception.EmailConflictException;
import io.github.biblioteca.digital.api.common.exception.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Getter
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERRO_GENERICA = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";
    private static final String MSG_CAMPOS_INVALIDOS = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.";
    private static final String MSG_PARAMETRO_INVALIDO = "O parametro de URL '%s' recebeu o valor '%s', que e de um tipo invalido. Corrija e informe um valor compativel com o tipo %s.";
    private static final String MSG_PARAMETRO_DESCONHECIDO = "Desconhecido";
    private static final String MSG_PROPRIEDADE_DESCONHECIDA = "A propriedade '%s' nao existe. Corrija ou remova essa propriedade e tente novamente.";
    private static final String MSG_BODY_INVALIDO = "O corpo da requisicao está invalido. Verifique erro de sintaxe.";
    private static final String MSG_RECURSO_INVALIDO = "O recurso %s, que você tentou acessar, e inexistente.";
    private static final String MSG_ACESSO_NAO_AUTORIZADO = "O acesso nao autorizado indica que uma solicitacao nao foi bem-sucedida por nao possuir credenciais de autenticacao validas para o recurso solicitado.";

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @ExceptionHandler(EmailConflictException.class)
    public ResponseEntity<Object> handleEmailConflictException(EmailConflictException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();
        JsonError jsonError = createProblemBuilder(status, TypeError.ERRO_NEGOCIO, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, jsonError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();
        JsonError jsonError = createProblemBuilder(status, TypeError.RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, jsonError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TypeError typeError = TypeError.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_GENERICA;
        log.error(ex.getMessage(), ex);
        JsonError jsonError = createProblemBuilder(status, typeError, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, jsonError, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        TypeError typeError = TypeError.PARAMETRO_INVALIDO;
        String requiredTypeName = Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName).orElse(MSG_PARAMETRO_DESCONHECIDO);
        String detail = String.format(MSG_PARAMETRO_INVALIDO, ex.getName(), ex.getValue(), requiredTypeName);
        JsonError jsonError = createProblemBuilder(status, typeError, detail)
                .userMessage(MSG_ERRO_GENERICA)
                .build();
        return handleExceptionInternal(ex, jsonError, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format(MSG_PROPRIEDADE_DESCONHECIDA, path);
        JsonError jsonError = createProblemBuilder(status, TypeError.PARAMETRO_INVALIDO, detail)
                .userMessage(MSG_ERRO_GENERICA)
                .build();
        return handleExceptionInternal(ex, jsonError, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format(MSG_PARAMETRO_INVALIDO, path, ex.getValue(), ex.getTargetType().getSimpleName());
        JsonError jsonError = createProblemBuilder(status, TypeError.PARAMETRO_INVALIDO, detail).userMessage(MSG_ERRO_GENERICA).build();
        return handleExceptionInternal(ex, jsonError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }
        JsonError jsonError = createProblemBuilder(status, TypeError.PARAMETRO_INVALIDO, MSG_BODY_INVALIDO)
                .userMessage(MSG_ERRO_GENERICA)
                .build();
        return handleExceptionInternal(ex, jsonError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = JsonError.builder().timestamp(OffsetDateTime.now())
                    .title(HttpStatus.valueOf(status.value()).getReasonPhrase()).status(status.value())
                    .userMessage(MSG_ERRO_GENERICA).build();
        } else if (body instanceof String) {
            body = JsonError.builder().timestamp(OffsetDateTime.now()).title((String) body).status(status.value())
                    .userMessage(MSG_ERRO_GENERICA).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = String.format(MSG_RECURSO_INVALIDO, ex.getRequestURL());
        JsonError jsonError = createProblemBuilder(status, TypeError.RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(MSG_ERRO_GENERICA)
                .build();
        return handleExceptionInternal(ex, jsonError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }


    private JsonError.JsonErrorBuilder createProblemBuilder(HttpStatusCode status, TypeError typeError, String detail) {
        return JsonError.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .title(typeError.getTitle())
                .detail(detail);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatusCode status, WebRequest request, BindingResult bindingResult) {
        List<Field> fields = bindingResult.getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            String name = objectError.getObjectName();
            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
            }
            return Field.of(name, message);
        }).collect(Collectors.toList());
        JsonError jsonError = createProblemBuilder(status, TypeError.DADOS_INVALIDOS, MSG_CAMPOS_INVALIDOS)
                .userMessage(MSG_CAMPOS_INVALIDOS)
                .fields(fields)
                .build();
        return handleExceptionInternal(ex, jsonError, headers, status, request);
    }

    private String joinPath(List<Reference> references) {
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }
}