package com.example.spring.service;

import com.example.spring.dto.ReviewCreateEditDto;
import com.example.spring.dto.ReviewReadDto;
import com.example.spring.mapper.ReviewCreateEditMapper;
import com.example.spring.mapper.ReviewReadMapper;
import com.example.spring.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReadMapper reviewReadMapper;
    private final ReviewCreateEditMapper reviewCreateEditMapper;

    public List<ReviewReadDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(reviewReadMapper::map)
                .toList();
    }

    public Optional<ReviewReadDto> findById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewReadMapper::map);
    }

    public ReviewReadDto create(ReviewCreateEditDto review) {
        return Optional.of(review)
                .map(reviewCreateEditMapper::map)
                .map(reviewRepository::save)
                .map(reviewReadMapper::map)
                .orElseThrow();
    }

    public Optional<ReviewReadDto> update(Long id, ReviewCreateEditDto review) {
        return reviewRepository.findById(id)
                .map(entity -> reviewCreateEditMapper.map(review, entity))
                .map(reviewRepository::saveAndFlush)
                .map(reviewReadMapper::map);
    }

    public boolean delete(Long id) {
        return reviewRepository.findById(id)
                .map(entity -> {
                    reviewRepository.delete(entity);
                    reviewRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
