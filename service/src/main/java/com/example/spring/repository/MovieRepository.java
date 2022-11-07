package com.example.spring.repository;

import com.example.spring.dto.MovieDto;
import com.example.spring.entity.Movie;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m from Movie m" +
            " where m.name = :name")
    Movie findMovieByName(String name);

    @Query("select m from Movie m")
    List<Movie> findLimitedMoviesOrderedByReleaseDate(Pageable pageable);

    @Query("select m from Movie m" +
            " join fetch m.reviews r" +
            " where r.grade >= :grade")
    List<Movie> findLimitedMoviesOrderedByReviewGrade(Pageable pageable, Double grade);

    @Query("select m from Movie m" +
            " join fetch  m.movieActors ma" +
            " join fetch  ma.actor a" +
            " where a.name = :actorName")
    List<Movie> findAllMovieByActorName(String actorName);

    @Query("select m.name, avg(r.grade) from Movie m" +
            " join m.reviews r" +
            " group by m.name")
    List<Object[]> findMovieWithAvgGradeOrderedByMovieName();
}


