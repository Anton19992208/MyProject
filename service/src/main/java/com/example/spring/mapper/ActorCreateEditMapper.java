package com.example.spring.mapper;

import com.example.spring.dto.ActorCreateEditDto;
import com.example.spring.entity.Actor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ActorCreateEditMapper implements Mapper<ActorCreateEditDto, Actor> {

    @Override
    public Actor map(ActorCreateEditDto object) {
        Actor actor = new Actor();
        copy(object, actor);
        return actor;
    }

    private void copy(ActorCreateEditDto object, Actor actor) {
        actor.setName(object.getName());
        actor.setSurname(object.getSurname());
        actor.setBirthdate(object.getBirthDate());
    }

    @Override
    public Actor map(ActorCreateEditDto fromObject, Actor toObject) {
        copy(fromObject, toObject);
        return toObject;
    }
}
