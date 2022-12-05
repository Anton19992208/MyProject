package com.example.spring.service;

import com.example.spring.dto.ActorCreateEditDto;
import com.example.spring.dto.ActorReadDto;
import com.example.spring.mapper.ActorCreateEditMapper;
import com.example.spring.mapper.ActorReadMapper;
import com.example.spring.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorReadMapper actorReadMapper;
    private final ActorCreateEditMapper actorCreateEditMapper;

    public List<ActorReadDto> findAll() {
        return actorRepository.findAll().stream()
                .map(actorReadMapper::map)
                .toList();
    }

    public Optional<ActorReadDto> findById(Long id) {
        return actorRepository.findById(id)
                .map(actorReadMapper::map);
    }

    @Transactional
    public ActorReadDto create(ActorCreateEditDto actorDto) {
        return Optional.of(actorDto)
                .map(actorCreateEditMapper::map)
                .map(actorRepository::save)
                .map(actorReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ActorReadDto> update(Long id, ActorCreateEditDto actor) {
        return actorRepository.findById(id)
                .map(entity -> actorCreateEditMapper.map(actor, entity))
                .map(actorRepository::saveAndFlush)
                .map(actorReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return actorRepository.findById(id)
                .map(entity -> {
                    actorRepository.delete(entity);
                    actorRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

