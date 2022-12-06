package com.example.spring.dto;

import com.example.spring.entity.Role;
import com.example.spring.validation.UserInfo;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.postgresql.util.LruCache;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
@FieldNameConstants
@UserInfo
public class UserCreateEditDto {
    String name;

    String surname;

    @Min(3)
    String password;

    String email;

    Role role;
}
