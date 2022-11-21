package com.example.spring.dto;

import com.example.spring.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    String name;
    String surname;
    String password;
    String email;
    Role role;
}
