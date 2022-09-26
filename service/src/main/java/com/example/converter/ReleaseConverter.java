package com.example.converter;

import com.example.entity.ReleaseDate;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.util.Optional;

public class ReleaseConverter implements AttributeConverter<ReleaseDate, Date> {


    @Override
    public Date convertToDatabaseColumn(ReleaseDate releaseDate) {
        return Optional.ofNullable(releaseDate)
                .map(ReleaseDate::releaseDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public ReleaseDate convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .map(ReleaseDate::new)
                .orElse(null);

    }
}
