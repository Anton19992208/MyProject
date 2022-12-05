package com.example.spring.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ActorCreateEditDto {
    String name;
    String surname;
    LocalDate birthDate;
}
