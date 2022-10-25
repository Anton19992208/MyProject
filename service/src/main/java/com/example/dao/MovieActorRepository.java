package com.example.dao;

import com.example.entity.BaseEntity;
import com.example.entity.MovieActor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MovieActorRepository extends DaoRepository<Long, MovieActor> {

    public MovieActorRepository(EntityManager entityManager) {
        super(MovieActor.class, entityManager);
    }
}
