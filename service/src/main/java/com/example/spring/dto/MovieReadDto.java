package com.example.spring.dto;

import com.example.spring.entity.Actor;
import com.example.spring.entity.Genre;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.List;

@Value
@FieldNameConstants
public class MovieReadDto {
    Long id;
    String name;
    String producer;
    LocalDate releaseDate;
    String country;
    Genre genre;
    String image;

}

