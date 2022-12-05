package com.example.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class MovieActorCreateEditDto {
    MovieReadDto movie;
    ActorReadDto actor;
}
