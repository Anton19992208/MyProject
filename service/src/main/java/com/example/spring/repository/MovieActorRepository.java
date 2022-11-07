package com.example.spring.repository;

import com.example.spring.entity.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface MovieActorRepository extends JpaRepository<MovieActor, Long> {

}
