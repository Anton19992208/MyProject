package com.example.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class ActorReadDto {
    Long id;
    String name;
    String surname;
    LocalDate birthDate;

}
