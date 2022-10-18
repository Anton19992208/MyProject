package com.example.dao;

import com.example.entity.BaseEntity;
import com.example.entity.MovieActor;

import javax.persistence.EntityManager;

public class MovieActorRepository extends DaoRepository<Long, MovieActor> {

    public MovieActorRepository(EntityManager entityManager){
        super(MovieActor.class, entityManager);
    }
}
