package com.example.spring.repository;

import com.example.spring.entity.Actor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ActorRepository extends RepositoryBase<Long, Actor> {

    public ActorRepository(EntityManager entityManager) {
        super(Actor.class, entityManager);

    }

}
