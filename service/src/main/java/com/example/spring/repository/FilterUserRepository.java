package com.example.spring.repository;

import com.example.spring.entity.User;
import com.example.spring.filter.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter);
}
