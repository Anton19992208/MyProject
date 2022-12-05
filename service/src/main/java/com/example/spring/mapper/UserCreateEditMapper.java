package com.example.spring.mapper;

import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setName(object.getName());
        user.setSurname(object.getSurname());
        user.setEmail(object.getEmail());
        user.setPassword(object.getPassword());
        user.setRole(object.getRole());
    }

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }
}
