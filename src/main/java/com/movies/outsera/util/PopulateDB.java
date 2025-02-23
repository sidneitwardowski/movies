package com.movies.outsera.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PopulateDB {
    private final MovieCsvUtil movieCsvUtil;

    @PostConstruct
    public void populate() throws IOException {
        log.info("Populando dados no H2DB::");
        movieCsvUtil.moviesFromCsvFile();
    }
}
