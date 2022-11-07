package com.example.spring.repository;

import com.example.spring.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
