package com.movies.outsera.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProducerDTO {
    @JsonProperty("producer")
    private String producer;
    @JsonProperty("previousWin")
    private Integer previousWin;
    @JsonProperty("followingWin")
    private Integer followingWin;
    @JsonProperty("interval")
    private Integer winInterval;
}
