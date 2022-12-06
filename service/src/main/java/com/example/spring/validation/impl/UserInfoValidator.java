package com.example.spring.validation.impl;

import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.validation.UserInfo;
import liquibase.util.StringUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.*;

public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {


    @Override
    public boolean isValid(UserCreateEditDto userCreateEditDto, ConstraintValidatorContext constraintValidatorContext) {
        return hasText(userCreateEditDto.getName()) || hasText(userCreateEditDto.getSurname());
    }
}
