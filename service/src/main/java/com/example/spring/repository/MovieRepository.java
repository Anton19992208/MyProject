package com.example.spring.repository;

import com.example.spring.entity.Movie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MovieRepository extends RepositoryBase<Long, Movie> {


    public MovieRepository(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }
}
