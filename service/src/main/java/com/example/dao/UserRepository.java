package com.example.dao;

import com.example.entity.User;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class UserRepository extends DaoRepository<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);

    }
}
