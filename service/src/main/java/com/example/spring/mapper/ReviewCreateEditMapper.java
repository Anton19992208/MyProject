package com.example.spring.mapper;

import ch.qos.logback.classic.util.CopyOnInheritThreadLocal;
import com.example.spring.dto.ReviewCreateEditDto;
import com.example.spring.entity.Movie;
import com.example.spring.entity.Review;
import com.example.spring.entity.User;
import com.example.spring.repository.MovieRepository;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewCreateEditMapper implements Mapper<ReviewCreateEditDto, Review> {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Override
    public Review map(ReviewCreateEditDto object) {
        Review review = new Review();
        copy(object, review);

        return review;
    }

    private void copy(ReviewCreateEditDto object, Review review) {
        review.setGrade(object.getGrade());
        review.setText(object.getText());
        review.setMovie(getMovie(object.getMovieId()));
        review.setUser(getUser(object.getUserId()));
    }

    @Override
    public Review map(ReviewCreateEditDto fromObject, Review toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private Movie getMovie(Long movieId) {
        return Optional.ofNullable(movieId)
                .flatMap(movieRepository::findById)
                .orElse(null);
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
