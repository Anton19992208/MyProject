package com.example.dao;

import com.example.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends DaoRepository<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);

    }
}
