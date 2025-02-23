package com.movies.outsera.util;

import com.movies.outsera.model.Movie;
import com.movies.outsera.model.Producer;
import com.movies.outsera.model.Studio;
import com.movies.outsera.service.MovieService;
import com.movies.outsera.service.ProducerService;
import com.movies.outsera.service.StudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.movies.outsera.enums.MovieCsvPositionEnum.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class MovieCsvUtil {

    @Value("${file.name}")
    private String fileName;
    private final ProducerService producerService;
    private final StudioService studioService;
    private final MovieService movieService;


    public void moviesFromCsvFile() throws IOException {
        Path filePath = Path.of(new ClassPathResource(fileName).getURI());
        log.info("Obtendo linhas do arquivo: " + filePath.toAbsolutePath());
        Files.lines(filePath)
                .skip(1)
                .collect(Collectors.toList())
                .forEach(line -> {

                    String[] values = line.split(";");

                    var valueProducers = values[PRODUCERS.getValue()]
                            .replaceAll(",?\\s+and\\s+", ", ")
                            .replaceAll("(?i)(\\w+)and\\s+", "$1, ")
                            .split(",");

                    var valueStudios = values[STUDIOS.getValue()].split(",");

                    List<Producer> producers = Arrays.stream(valueProducers)
                            .map(String::trim)
                            .distinct()
                            .map(name -> producerService.getOrCreateProducer(Producer.builder().name(name).build()))
                            .collect(Collectors.toList());

                    List<Studio> studios = Arrays.stream(valueStudios)
                            .map(String::trim)
                            .distinct()
                            .map(name -> studioService.getOrCreateStudio(Studio.builder().name(name).build()))
                            .collect(Collectors.toList());

                    var movie = Movie.builder()
                            .yearIndication(Integer.parseInt(values[YEAR.getValue()]))
                            .title(values[TITLE.getValue()])
                            .winner(isWinner(values))
                            .build();

                    final Movie savedMovie = movieService.save(movie);

                    producers.forEach(producer -> {
                        if (producer.getMovies() == null) {
                            producer.setMovies(new ArrayList<>());
                        }
                        producer.setMovies(List.of(savedMovie));
                    });

                    studios.forEach(studio -> {
                        if (studio.getMovies() == null) {
                            studio.setMovies(new ArrayList<>());
                        }
                        studio.setMovies(List.of(savedMovie));
                    });

                    movie.setProducers(producers);
                    movie.setStudios(studios);

                    movieService.save(movie);

                    log.info("Gravando no banco de dados o filme {}.", movie.getTitle());

                });
    }

    private Boolean isWinner(String[] values) {

        return Optional.ofNullable(values)
                .filter(val -> val.length > WINNER.getValue())
                .map(arr -> arr[WINNER.getValue()])
                .map(result -> result.equalsIgnoreCase("yes"))
                .orElse(false);
    }
}
