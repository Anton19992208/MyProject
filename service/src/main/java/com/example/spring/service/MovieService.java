package com.example.spring.service;

import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.filter.MovieFilter;
import com.example.spring.mapper.MovieCreateEditMapper;
import com.example.spring.mapper.MovieReadMapper;
import com.example.spring.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.SecondaryTable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieReadMapper movieReadMapper;
    private final MovieCreateEditMapper createEditMapper;

    public List<MovieReadDto> findAll() {
        return movieRepository.findAll().stream()
                .map(movieReadMapper::map)
                .toList();
    }

    public List<MovieReadDto> findAll(MovieFilter movieFilter) {
        return movieRepository.findAllByFilter(movieFilter).stream()
                .map(movieReadMapper::map)
                .toList();
    }

    public Optional<MovieReadDto> findById(Long id) {
        return movieRepository.findById(id)
                .map(movieReadMapper::map);
    }

    @Transactional
    public MovieReadDto create(MovieCreateEditDto movieDto) {
        return Optional.of(movieDto)
                .map(createEditMapper::map)
                .map(movieRepository::save)
                .map(movieReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<MovieReadDto> update(Long id, MovieCreateEditDto movieDto) {
        return movieRepository.findById(id)
                .map(entity -> createEditMapper.map(movieDto, entity))
                .map(movieRepository::saveAndFlush)
                .map(movieReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return movieRepository.findById(id)
                .map(entity -> {
                    movieRepository.delete(entity);
                    movieRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
