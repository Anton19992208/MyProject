package com.example.dao;

import com.example.dto.MovieDto;
import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import com.example.entity.Review;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.example.entity.QActor.actor;
import static com.example.entity.QMovie.movie;
import static com.example.entity.QReview.review;
import static com.example.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieDaoDsl {

    private static final MovieDaoDsl INSTANCE = new MovieDaoDsl();

    public List<Movie> findAll(Session session) {

        return new JPAQuery<Movie>(session)
                .select(movie)
                .from(movie)
                .fetch();
    }

    public List<Movie> findByName(Session session, String name) {

        return new JPAQuery<Movie>(session)
                .select(movie)
                .from(movie)
                .where(movie.name.eq(name))
                .fetch();

    }

    public List<Movie> findLimitedMoviesOrderedByReleaseDate(Session session, int limit) {

        return new JPAQuery<Movie>(session)
                .select(movie)
                .from(movie)
                .orderBy(movie.releaseDate.asc())
                .limit(limit)
                .fetch();

    }

    public List<Actor> findAllActorsByMovieName(Session session, String movieName) {

        return new JPAQuery<Actor>(session)
                .select(actor)
                .from(movie)
                .join(movie.movieActors)
                .where(movie.name.eq(movieName))
                .fetch();

    }

    public List<Review> findAllReviewByMovieName(Session session, String movieName) {

        return new JPAQuery<Review>(session)
                .select(review)
                .from(review)
                .join(review.user, user)
                .join(review.movie, movie)
                .where(movie.name.eq(movieName))
                .orderBy(review.grade.asc(), movie.name.asc())
                .fetch();

    }

    public Double findAverageGradeByMovieName(Session session, String movieName, String producer) {

        return new JPAQuery<Double>(session)
                .select(review.grade.avg())
                .from(review)
                .join(review.movie, movie)
                .where(movie.name.eq(movieName)
                        .and(movie.producer.eq(producer)))
                .fetchOne();

    }

    public List<com.querydsl.core.Tuple> findMovieNameByAvgGradeOrderedByMovieName(Session session) {

        return new JPAQuery<com.querydsl.core.Tuple>(session)
                .select(movie.name, review.grade.avg())
                .from(movie)
                .join(movie.reviews, review)
                .groupBy(movie.name)
                .orderBy(movie.name.asc())
                .fetch();

    }

    public List<Tuple> findSetOfMoviesWhereAvgGradeIsMoreThenAvgGradeOfAllMoviesOrderedByName(Session session) {

        return new JPAQuery<Tuple>(session)
                .select(movie, review.grade.avg())
                .from(movie)
                .join(movie.reviews, review)
                .groupBy(movie.id)
                .having(review.grade.avg().gt(
                        new JPAQuery<Double>(session)
                                .select(review.grade.avg())
                                .from(review)
                ))
                .orderBy(movie.name.asc())
                .fetch();

    }

    public static MovieDaoDsl getInstance() {
        return INSTANCE;
    }
}
