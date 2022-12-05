package com.example.spring.dto;

import lombok.Value;

@Value
public class MovieActorReadDto {
    Long Id;
    MovieReadDto movie;
    ActorReadDto actor;


}
