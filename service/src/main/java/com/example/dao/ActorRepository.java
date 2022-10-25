package com.example.dao;

import com.example.entity.Actor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ActorRepository extends DaoRepository<Long, Actor> {

    public ActorRepository(EntityManager entityManager) {
        super(Actor.class, entityManager);
    }
}
