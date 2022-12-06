package com.example.spring.service;

import com.example.spring.dto.MovieActorCreateEditDto;
import com.example.spring.dto.MovieActorReadDto;
import com.example.spring.mapper.MovieActorCreateEditMapper;
import com.example.spring.mapper.MovieActorReadMapper;
import com.example.spring.repository.MovieActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieActorService {

    private final MovieActorRepository movieActorRepository;
    private final MovieActorReadMapper movieActorReadMapper;
    private final MovieActorCreateEditMapper movieActorCreateEditMapper;

    public List<MovieActorReadDto> findAll(){
        return movieActorRepository.findAll().stream()
                .map(movieActorReadMapper::map)
                .toList();
    }

    public Optional<MovieActorReadDto> findById(Long id){
        return movieActorRepository.findById(id)
                .map(movieActorReadMapper::map);
    }

    public MovieActorReadDto create(MovieActorCreateEditDto movieActor){
        return Optional.of(movieActor)
                .map(movieActorCreateEditMapper::map)
                .map(movieActorRepository::save)
                .map(movieActorReadMapper::map)
                .orElseThrow();
    }

    public Optional<MovieActorReadDto> update(Long id, MovieActorCreateEditDto movieActor){
        return movieActorRepository.findById(id)
                .map(entity -> movieActorCreateEditMapper.map(movieActor, entity))
                .map(movieActorRepository::saveAndFlush)
                .map(movieActorReadMapper::map);
    }

    public boolean delete(Long id){
       return movieActorRepository.findById(id)
                .map(entity -> {
                    movieActorRepository.delete(entity);
                    movieActorRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
