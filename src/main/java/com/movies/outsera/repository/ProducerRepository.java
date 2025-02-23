package com.movies.outsera.repository;

import com.movies.outsera.model.Producer;
import com.movies.outsera.repository.helper.ProducerRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long>, ProducerRepositoryQuery {

    Optional<Producer> findByName(String name);
}