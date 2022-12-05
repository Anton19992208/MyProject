package com.example.spring.repository;

import com.example.spring.entity.Actor;
import com.example.spring.entity.Movie;
import com.example.spring.repository.predicate.QPredicates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public interface ActorRepository extends JpaRepository<Actor, Long>, QuerydslPredicateExecutor<Actor> {

    @Query("select a from Actor a " +
            "join fetch a.movieActors ma" +
            " join fetch ma.movie m" +
            " where m.name = :movieName")
    List<Actor> findActorByMovieName(String movieName);


    @Query("select a from Actor a" +
            " where a.surname = :surname ")
    Actor findBySurname(String surname);
}
