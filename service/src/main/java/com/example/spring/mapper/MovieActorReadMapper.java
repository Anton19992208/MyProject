package com.example.spring.mapper;

import com.example.spring.dto.ActorReadDto;
import com.example.spring.dto.MovieActorReadDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.entity.MovieActor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieActorReadMapper implements Mapper <MovieActor, MovieActorReadDto> {

    private final MovieReadMapper movieReadMapper;
    private final ActorReadMapper actorReadMapper;

    @Override
    public MovieActorReadDto map(MovieActor object) {
        MovieReadDto movie = Optional.ofNullable(object.getMovie())
                .map(movieReadMapper::map)
                .orElse(null);
        ActorReadDto actor = Optional.ofNullable(object.getActor())
                .map(actorReadMapper::map)
                .orElse(null);

        return new MovieActorReadDto(object.getId(), movie, actor);
    }
}
