package com.example.spring.repository;

import com.example.spring.filter.ReviewFilter;
import com.example.spring.entity.Movie;
import com.example.spring.filter.MovieFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterMovieRepository {

    List<Movie> findAllByFilter(MovieFilter movieFilter);

    List<Movie> findAllMoviesOrderedByReviewGrade(String name, Pageable pageable);

}
