package com.example.spring.repository.filter;

import com.example.entity.QUser;
import com.example.spring.entity.User;
import com.example.spring.filter.UserFilter;
import com.example.spring.repository.FilterUserRepository;
import com.example.spring.repository.predicate.QPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        Predicate predicates = QPredicates.builder()
                .add(filter.name(), user.name::containsIgnoreCase)
                .add(filter.surname(), user.surname::containsIgnoreCase)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicates)
                .fetch();
    }
}
