package com.movies.outsera.service;

import com.movies.outsera.dto.ProducerDTO;
import com.movies.outsera.dto.ProducerIntervalResponseDTO;
import com.movies.outsera.enums.TypeInterval;
import com.movies.outsera.model.Producer;
import com.movies.outsera.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProducerService {
    private final ProducerRepository producerRepository;

    public Producer getOrCreateProducer(Producer producer) {
        return producerRepository.findByName(producer.getName())
                .orElseGet(() -> producerRepository.save(producer));
    }

    public ProducerIntervalResponseDTO findWinsIntervals() {
        log.info("Obtendo o menor intervalo (0 ou 1 ano)");
        List<ProducerDTO> minList = producerRepository.findWinsIntervals(TypeInterval.MIN);
        log.info("Menor intervalo: {}", minList);

        log.info("Obtendo o maior intervalo consolidado por produtor");
        List<ProducerDTO> maxList = producerRepository.findWinsIntervals(TypeInterval.MAX);
        log.info("Maior intervalo: {}", maxList);

        return new ProducerIntervalResponseDTO(minList, maxList);
    }
}
