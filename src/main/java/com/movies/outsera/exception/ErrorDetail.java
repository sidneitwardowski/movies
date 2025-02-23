package com.movies.outsera.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Detalhes sobre um erro ocorrido na API")
public record ErrorDetail(
        @Schema(description = "Timestamp do erro", example = "2024-01-30T15:23:01.123456")
        LocalDateTime timestamp,

        @Schema(description = "Código HTTP do erro", example = "400")
        int status,

        @Schema(description = "Mensagem de erro", example = "Requisição inválida")
        String message,

        @Schema(description = "Nome da exceção lançada", example = "IllegalArgumentException")
        String exception,

        @Schema(description = "Detalhes adicionais do erro", example = "[\"Campo X inválido\", \"Campo Y não pode ser nulo\"]")
        List<String> details
) {
}