package com.example.converter;

import com.example.entity.Birthday;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;
import java.sql.Date;


    public class BirthdayConverter implements AttributeConverter<Birthday, Date> {


        @Override
        public Date convertToDatabaseColumn(Birthday birthday) {
            return Optional.ofNullable(birthday)
                    .map(Birthday::birthDay)
                    .map(Date::valueOf)
                    .orElse(null);
        }

        @Override
        public Birthday convertToEntityAttribute(Date date) {
            return Optional.ofNullable(date)
                    .map(Date::toLocalDate)
                    .map(Birthday::new)
                    .orElse(null);
        }
    }

