package com.example.spring.repository;

import com.example.spring.entity.Movie;
import com.example.spring.filter.MovieFilter;
import org.springframework.stereotype.Component;

import java.util.List;

public interface FilterMovieRepository {

    List<Movie> findAllByFilter(MovieFilter movieFilter);
}
