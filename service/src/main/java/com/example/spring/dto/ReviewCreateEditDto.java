package com.example.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class ReviewCreateEditDto {
    String text;
    Double grade;
    Long MovieId;
    Long UserId;

}
