package com.example.spring.service;

import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.entity.Movie;
import com.example.spring.filter.MovieFilter;
import com.example.spring.mapper.MovieCreateEditMapper;
import com.example.spring.mapper.MovieReadMapper;
import com.example.spring.repository.MovieRepository;
import com.example.spring.repository.predicate.QPredicates;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.entity.QMovie.movie;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieReadMapper movieReadMapper;
    private final MovieCreateEditMapper createEditMapper;
    private final ImageService imageService;

    public Page<MovieReadDto> findAll(MovieFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.name(), movie.name::containsIgnoreCase)
                .add(filter.genre(), movie.genre::eq)
                .add(filter.releaseDate(), movie.releaseDate::before)
                .build();
        return movieRepository.findAll(predicate, pageable)
                .map(movieReadMapper::map);
    }

    public List<MovieReadDto> findAll(MovieFilter movieFilter) {
        return movieRepository.findAllByFilter(movieFilter).stream()
                .map(movieReadMapper::map)
                .toList();
    }

    public List<MovieReadDto> findAll(String name, Pageable pageable){
        return movieRepository.findAllMoviesOrderedByReviewGrade(name, pageable).stream()
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
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return createEditMapper.map(dto);
                })
                .map(movieRepository::save)
                .map(movieReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<MovieReadDto> update(Long id, MovieCreateEditDto movieDto) {
        return movieRepository.findById(id)
                .map(entity -> {
                    uploadImage(movieDto.getImage());
                    return createEditMapper.map(movieDto, entity);
                })
                .map(movieRepository::saveAndFlush)
                .map(movieReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Long id)  {
        return movieRepository.findById(id)
                .map(Movie::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
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

    @SneakyThrows
    public void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
