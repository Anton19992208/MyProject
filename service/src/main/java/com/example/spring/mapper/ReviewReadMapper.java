package com.example.spring.mapper;

import com.example.spring.dto.MovieReadDto;
import com.example.spring.dto.ReviewReadDto;
import com.example.spring.dto.UserReadDto;
import com.example.spring.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewReadMapper implements Mapper<Review, ReviewReadDto> {

    private final MovieReadMapper movieReadMapper;
    private final UserReadMapper userReadMapper;

    @Override
    public ReviewReadDto map(Review object) {
        MovieReadDto movie = Optional.ofNullable(object.getMovie())
                .map(movieReadMapper::map)
                .orElse(null);
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);

        return new ReviewReadDto(object.getId(),
                object.getText(),
                object.getGrade(),
                movie,
                user
        );
    }
}
