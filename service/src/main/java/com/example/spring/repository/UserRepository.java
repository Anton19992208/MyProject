package com.example.spring.repository;

import com.example.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor {

}
