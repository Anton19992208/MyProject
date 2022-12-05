package com.example.spring.repository.predicate;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class QPredicates {

    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicates builder(){
        return new QPredicates();
    }

    public <T> QPredicates add(T object, Function<T, Predicate> function){
        if(object != null){
            predicates.add(function.apply(object));
        }
        return this;
    }

    public <T> QPredicates add2(Collection<? extends T>collection, Function<Collection<? extends T>, Predicate> function){
        if(!collection.isEmpty()){
            predicates.add(function.apply(collection));
        }
        return this;
    }

    public Predicate build(){
        return Optional.ofNullable(ExpressionUtils.allOf(predicates))
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }

    public Predicate buildOr(){
        return Optional.ofNullable(ExpressionUtils.anyOf(predicates))
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }

}

