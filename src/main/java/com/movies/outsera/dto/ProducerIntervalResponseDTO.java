package com.movies.outsera.dto;

import java.util.List;

public record ProducerIntervalResponseDTO(
        List<ProducerDTO> min,
        List<ProducerDTO> max
) {
}
