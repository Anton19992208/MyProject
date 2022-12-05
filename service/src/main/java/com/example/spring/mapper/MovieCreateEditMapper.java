package com.example.spring.mapper;

import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.entity.Movie;
import liquibase.pro.packaged.M;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.*;

@Component
public class MovieCreateEditMapper implements Mapper<MovieCreateEditDto, Movie> {

    @Override
    public Movie map(MovieCreateEditDto object) {
        Movie movie = new Movie();
        copy(object, movie);

        return movie;
    }

    private void copy(MovieCreateEditDto object, Movie movie) {
        movie.setName(object.getName());
        movie.setGenre(object.getGenre());
        movie.setProducer(object.getProducer());
        movie.setCountry(object.getCountry());
        movie.setReleaseDate(object.getReleaseDate());

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> movie.setImage(image.getOriginalFilename()));
    }

    @Override
    public Movie map(MovieCreateEditDto fromObject, Movie toObject) {
        copy(fromObject, toObject);
        return toObject;
    }
}
