package com.example.spring.filter;

import com.example.spring.entity.Genre;

import java.time.LocalDate;

public record MovieFilter(String name,
                          LocalDate releaseDate,
                          Genre genre) {
}
