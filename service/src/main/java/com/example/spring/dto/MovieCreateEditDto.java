package com.example.spring.dto;

import com.example.spring.entity.Actor;
import com.example.spring.entity.Genre;
import com.example.spring.entity.MovieActor;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Value
public class MovieCreateEditDto {

    String name;
    String producer;
    LocalDate releaseDate;
    String country;
    Genre genre;
    MultipartFile image;

}

