package com.example.spring.repository;

import com.example.spring.entity.MovieActor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MovieActorRepository extends RepositoryBase<Long, MovieActor> {

    public MovieActorRepository(EntityManager entityManager) {
        super(MovieActor.class,  entityManager );

    }
}
