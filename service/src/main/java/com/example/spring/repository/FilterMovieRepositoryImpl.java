package com.example.spring.repository;

import com.example.spring.entity.Movie;
import com.example.spring.filter.MovieFilter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterMovieRepositoryImpl implements FilterMovieRepository {

    private final EntityManager entityManager;

    @Override
    public List<Movie> findAllByFilter(MovieFilter movieFilter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Movie.class);

        var movie = criteria.from(Movie.class);
        criteria.select(movie);

        List<Predicate> predicate = new ArrayList<>();
        if (movieFilter.name() != null) {
            predicate.add(cb.like(movie.get("name"), movieFilter.name()));
        }
        if (movieFilter.releaseDate() != null) {
            predicate.add(cb.lessThan(movie.get("releaseDate"), movieFilter.releaseDate()));
        }
        if (movieFilter.genre() != null) {
            predicate.add(cb.equal(movie.get("genre"), movieFilter.genre()));
        }
        criteria.where(predicate.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }
}
