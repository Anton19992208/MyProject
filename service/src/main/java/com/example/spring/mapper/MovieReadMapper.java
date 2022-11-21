package com.example.spring.mapper;

import com.example.spring.dto.MovieReadDto;
import com.example.spring.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieReadMapper implements Mapper<Movie, MovieReadDto> {

    @Override
    public MovieReadDto map(Movie object) {
        return new MovieReadDto(
                object.getId(),
                object.getName(),
                object.getProducer(),
                object.getReleaseDate(),
                object.getCountry(),
                object.getGenre()
        );
    }
}
