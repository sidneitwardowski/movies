package com.movies.outsera.repository.impl;

import com.movies.outsera.dto.ProducerDTO;
import com.movies.outsera.enums.TypeInterval;
import com.movies.outsera.repository.helper.ProducerRepositoryQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProducerRepositoryImpl implements ProducerRepositoryQuery {
    private final EntityManager manager;


    @Override
    public List<ProducerDTO> findWinsIntervals(TypeInterval interval) {

        Query query = manager.createNativeQuery(getSql(interval.getValue()), ProducerDTO.class);
        return query.getResultList();
    }

    private String getSql(String tipo) {
        return String.format("""
                SELECT
                   main.producer,
                   MIN(main.previousWin) AS previousWin,
                   MIN(main.followingWin) AS followingWin,
                   main.winInterval
               FROM (
                   SELECT
                       current.producer,
                       current.year_indication AS previousWin,
                       next.year_indication AS followingWin,
                       (next.year_indication - current.year_indication) AS winInterval
                   FROM (
                       SELECT
                           p.name AS producer,
                           m.year_indication,
                           (SELECT COUNT(*) FROM movies m2
                            JOIN movie_producer mp2 ON mp2.movie_id = m2.id
                            WHERE mp2.producer_id = mp.producer_id
                              AND m2.winner = TRUE
                              AND m2.year_indication <= m.year_indication) AS row_num
                       FROM
                           producers p
                           JOIN movie_producer mp ON mp.producer_id = p.id
                           JOIN movies m ON m.id = mp.movie_id
                       WHERE m.winner = TRUE
                   ) AS current
                   JOIN (
                       SELECT
                           p.name AS producer,
                           m.year_indication,
                           (SELECT COUNT(*) FROM movies m2
                            JOIN movie_producer mp2 ON mp2.movie_id = m2.id
                            WHERE mp2.producer_id = mp.producer_id
                              AND m2.winner = TRUE
                              AND m2.year_indication <= m.year_indication) AS row_num
                       FROM
                           producers p
                           JOIN movie_producer mp ON mp.producer_id = p.id
                           JOIN movies m ON m.id = mp.movie_id
                       WHERE m.winner = TRUE
                   ) AS next
                   ON current.producer = next.producer
                   AND current.row_num = next.row_num - 1
                   GROUP BY
                       current.producer,
                       current.year_indication,
                       next.year_indication
               ) AS main
               WHERE main.winInterval = (
                   SELECT %s(winInterval)
                   FROM (
                       SELECT
                           (next.year_indication - current.year_indication) AS winInterval
                       FROM (
                           SELECT
                               p.name AS producer,
                               m.year_indication,
                               (SELECT COUNT(*) FROM movies m2
                                JOIN movie_producer mp2 ON mp2.movie_id = m2.id
                                WHERE mp2.producer_id = mp.producer_id
                                  AND m2.winner = TRUE
                                  AND m2.year_indication <= m.year_indication) AS row_num
                           FROM
                               producers p
                               JOIN movie_producer mp ON mp.producer_id = p.id
                               JOIN movies m ON m.id = mp.movie_id
                           WHERE m.winner = TRUE
                       ) AS current
                       JOIN (
                           SELECT
                               p.name AS producer,
                               m.year_indication,
                               (SELECT COUNT(*) FROM movies m2
                                JOIN movie_producer mp2 ON mp2.movie_id = m2.id
                                WHERE mp2.producer_id = mp.producer_id
                                  AND m2.winner = TRUE
                                  AND m2.year_indication <= m.year_indication) AS row_num
                           FROM
                               producers p
                               JOIN movie_producer mp ON mp.producer_id = p.id
                               JOIN movies m ON m.id = mp.movie_id
                           WHERE m.winner = TRUE
                       ) AS next
                       ON current.producer = next.producer
                       AND current.row_num = next.row_num - 1
                   ) AS innerIntervals
               )
               GROUP BY
                   main.producer,
                   main.previousWin,
                   main.followingWin,
                   main.winInterval
               ORDER BY
                   main.winInterval DESC,
                   previousWin ASC;
                """, tipo);
    }
}
