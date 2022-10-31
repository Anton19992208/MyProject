package com.example.spring.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReviewFilter {
    String movieName;

}
