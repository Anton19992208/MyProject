package com.example.spring.repository.predicate;

import com.querydsl.core.types.Predicate;
import org.springframework.objenesis.ObjenesisBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class QPredicates {

    private final List<Predicate> predicates = new ArrayList<>();

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


}
