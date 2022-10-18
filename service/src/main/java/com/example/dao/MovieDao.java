package com.example.dao;

import com.example.dto.MovieDto;
import com.example.entity.Actor;
import com.example.entity.Actor_;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import com.example.entity.MovieActor_;
import com.example.entity.Movie_;
import com.example.entity.Review;
import com.example.entity.Review_;
import com.example.entity.User_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieDao {

    private static final MovieDao INSTANCE = new MovieDao();

    public List<Movie> findAll(Session session) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Movie.class);
        var movie = criteria.from(Movie.class);

        criteria.select(movie);

        return session.createQuery(criteria)
                .list();
    }

    public List<Movie> findByName(Session session, String name) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Movie.class);
        var movie = criteria.from(Movie.class);

        criteria.select(movie).where(
                cb.equal(movie.get(Movie_.name), name)
        );

        return session.createQuery(criteria)
                .list();
    }

    public List<Movie> findLimitedMoviesOrderedByReleaseDate(Session session, int limit) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Movie.class);
        var movie = criteria.from(Movie.class);

        criteria.select(movie).orderBy(
                cb.asc(movie.get(Movie_.releaseDate))
        );

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
    }

    public List<Actor> findAllActorsByMovieName(Session session, String movieName) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Actor.class);
        var actors = criteria.from(Actor.class);
        var movieActors = actors.join(Actor_.movieActors);
        var movie = movieActors.join(MovieActor_.movie);

        criteria.select(actors).where(
                cb.equal(movie.get(Movie_.name.getName()), movieName)
        );

        return session.createQuery(criteria)
                .list();


    }

    public List<Review> findAllReviewByMovieName(Session session, String movieName) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Review.class);
        var review = criteria.from(Review.class);
        var user = review.join(Review_.user);
        var movie = review.join(Review_.movie);

        criteria.select(review).where(
                        cb.equal(movie.get(Movie_.name), movieName)
                )
                .orderBy(
                        cb.asc(user.get(User_.name)),
                        cb.asc(review.get(Review_.grade))
                );

        return session.createQuery(criteria)
                .list();

    }

    public Double findAverageGradeByMovieName(Session session, String movieName, String producer) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Double.class);
        var review = criteria.from(Review.class);
        var movie = review.join(Review_.movie);

        List<Predicate> predicates = new ArrayList<>();
        if (movieName != null) {
            predicates.add(cb.equal(movie.get(Movie_.name), movieName));
        }
        if (producer != null) {
            predicates.add(cb.equal(movie.get(Movie_.producer), producer));
        }
        criteria.select(cb.avg(review.get(Review_.grade))).where(
                predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria)
                .uniqueResult();
    }

    public List<MovieDto> findMovieNameByAvgGradeOrderedByMovieName(Session session) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(MovieDto.class);
        var movie = criteria.from(Movie.class);
        var review = movie.join(Movie_.reviews);

        criteria.select(
                        cb.construct(MovieDto.class,
                                movie.get(Movie_.name),
                                cb.avg(review.get(Review_.grade)))
                )
                .groupBy(movie.get(Movie_.name))
                .orderBy(cb.asc(movie.get(Movie_.name)));

        return session.createQuery(criteria)
                .list();
    }

    public List<Tuple> findSetOfMoviesWhereAvgGradeIsMoreThenAvgGradeOfAllMoviesOrderedByName(Session session) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Tuple.class);
        var movie = criteria.from(Movie.class);
        var review = movie.join(Movie_.reviews);

        var subquery = criteria.subquery(Double.class);
        var reviewSubquery = subquery.from(Review.class);

        criteria.select(
                        cb.tuple(
                                movie,
                                cb.avg(review.get(Review_.grade))
                        )
                )
                .groupBy(movie.get(Movie_.id))
                .having(cb.gt(
                        cb.avg(review.get(Review_.grade)),
                        subquery.select(cb.avg(reviewSubquery.get(Review_.grade)))
                ))
                .orderBy(cb.asc(movie.get(Movie_.name)));

        return session.createQuery(criteria)
                .list();

    }


    public static MovieDao getInstance() {
        return INSTANCE;
    }
}
