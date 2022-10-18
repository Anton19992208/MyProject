package com.example.dao;

import com.example.entity.Movie;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class MovieRepository extends DaoRepository<Long, Movie> {

    public MovieRepository(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }
}
