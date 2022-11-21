package com.example.spring.dto;

import com.example.spring.entity.Genre;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class MovieReadDto {
    Long id;
    String name;
    String producer;
    LocalDate releaseDate;
    String country;
    Genre genre;

}

