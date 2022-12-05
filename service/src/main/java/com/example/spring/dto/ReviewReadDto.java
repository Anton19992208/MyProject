package com.example.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class ReviewReadDto {
    Long id;
    String text;
    Double grade;
    MovieReadDto movie;
    UserReadDto user;

}
