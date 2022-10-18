package com.example.dao;

import com.example.entity.Review;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class ReviewRepository extends DaoRepository<Long, Review> {

    public ReviewRepository(EntityManager entityManager) {
        super(Review.class, entityManager);
    }
}
