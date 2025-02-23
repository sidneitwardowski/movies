package com.movies.outsera.controller;

import com.movies.outsera.dto.ProducerIntervalResponseDTO;
import com.movies.outsera.exception.ErrorDetail;
import com.movies.outsera.service.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final ProducerService movieService;

    @Operation(summary = "Dados dos produtores", description = "Obtem os produtores com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição OK",
                    content = @Content(schema = @Schema(implementation = ProducerIntervalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })

    @GetMapping("/ranking")
    public ResponseEntity<ProducerIntervalResponseDTO> getProducerIntervalResponse() {
        return ResponseEntity.ok(movieService.findWinsIntervals());
    }
}
