package com.movies.outsera.service;

import com.movies.outsera.model.Studio;
import com.movies.outsera.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudioService {
    private final StudioRepository studioRepository;

    public Studio getOrCreateStudio(Studio studio) {
        return studioRepository.findByName(studio.getName())
                .orElseGet(() -> studioRepository.save(studio));
    }

}
