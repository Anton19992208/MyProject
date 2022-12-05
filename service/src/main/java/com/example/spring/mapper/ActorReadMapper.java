package com.example.spring.mapper;

import com.example.spring.dto.ActorReadDto;
import com.example.spring.entity.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorReadMapper implements Mapper<Actor, ActorReadDto> {

    @Override
    public ActorReadDto map(Actor object) {
        return new ActorReadDto(
                object.getId(),
                object.getName(),
                object.getSurname(),
                object.getBirthdate()
        );
    }
}
