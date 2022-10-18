package com.example.dao;

import com.example.entity.Actor;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class ActorRepository extends DaoRepository<Long, Actor> {

    public ActorRepository(EntityManager entityManager) {
        super(Actor.class, entityManager);
    }
}
