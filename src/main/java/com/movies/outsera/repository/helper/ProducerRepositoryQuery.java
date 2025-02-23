package com.movies.outsera.repository.helper;


import com.movies.outsera.dto.ProducerDTO;
import com.movies.outsera.enums.TypeInterval;

import java.util.List;

public interface ProducerRepositoryQuery {
    List<ProducerDTO> findWinsIntervals(TypeInterval interval);
}
