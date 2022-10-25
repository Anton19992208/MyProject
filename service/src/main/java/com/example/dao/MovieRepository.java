package com.example.dao;

import com.example.entity.Movie;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class MovieRepository extends DaoRepository<Long, Movie> {

    public MovieRepository(EntityManager entityManager) {
        super(Movie.class, entityManager);
    }
}
