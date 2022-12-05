package com.example.spring.dto;

import com.example.spring.entity.Genre;
import lombok.Value;

import java.time.LocalDate;

@Value
public class MovieCreateEditDto {
    String name;
    String producer;
    LocalDate releaseDate;
    String country;
    Genre genre;

}

