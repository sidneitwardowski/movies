package com.movies.outsera.service;

import com.movies.outsera.model.Movie;
import com.movies.outsera.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }


}
