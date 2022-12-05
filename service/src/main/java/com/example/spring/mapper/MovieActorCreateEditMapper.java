package com.example.spring.mapper;

import com.example.spring.dto.MovieActorCreateEditDto;
import com.example.spring.entity.Actor;
import com.example.spring.entity.Movie;
import com.example.spring.entity.MovieActor;
import com.example.spring.repository.ActorRepository;
import com.example.spring.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieActorCreateEditMapper implements Mapper<MovieActorCreateEditDto, MovieActor> {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Override
    public MovieActor map(MovieActorCreateEditDto object) {
        MovieActor movieActor = new MovieActor();
        copy(object, movieActor);
        return movieActor;
    }

    private void copy(MovieActorCreateEditDto object, MovieActor movieActor) {
        movieActor.setActor(getActor(object.getActor().getId()));
        movieActor.setMovie(getMovie(object.getMovie().getId()));
    }

    @Override
    public MovieActor map(MovieActorCreateEditDto fromObject, MovieActor toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private Movie getMovie(Long movieId){
        return Optional.ofNullable(movieId)
                .flatMap(movieRepository::findById)
                .orElse(null);
    }

    private Actor getActor(Long actorId){
        return Optional.ofNullable(actorId)
                .flatMap(actorRepository::findById)
                .orElse(null);
    }


}
