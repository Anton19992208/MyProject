package com.example.spring.repository.filter;

import com.example.spring.entity.Movie_;
import com.example.spring.entity.Review;
import com.example.spring.entity.Review_;
import com.example.spring.filter.ReviewFilter;
import com.example.spring.entity.Movie;
import com.example.spring.filter.MovieFilter;
import com.example.spring.repository.FilterMovieRepository;
import com.example.spring.repository.predicate.QPredicates;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static com.example.entity.QMovie.movie;
import static com.example.entity.QReview.review;
import static com.example.spring.entity.Movie_.reviews;

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

    @Override
    public List<Movie> findAllMoviesOrderedByReviewGrade(String name, Pageable pageable) {
//        QPredicates.builder()
//                .add(filter.grade(), review.grade::isNotNull)
//                .build();

        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Movie.class);
        var movie = criteria.from(Movie.class);
        var review = movie.join(reviews);

        criteria.select(movie)
                .where(cb.equal(movie.get(Movie_.name), name)
                )
                .orderBy(
                        cb.asc(review.get(Review_.GRADE))
                );
        return entityManager.createQuery(criteria)
                .getResultList();
    }
}
