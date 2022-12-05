package com.example.spring.mapper;

import com.example.spring.dto.UserReadDto;
import com.example.spring.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getName(),
                object.getSurname(),
                object.getPassword(),
                object.getEmail(),
                object.getRole()
        );
    }
}
