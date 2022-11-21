package service;

import com.example.spring.dto.ActorCreateEditDto;
import com.example.spring.dto.ActorReadDto;
import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.entity.Genre;
import com.example.spring.service.ActorService;
import com.example.spring.service.MovieService;
import jpaintegration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class ActorServiceIT extends IntegrationTestBase{

    private final ActorService actorService;
    private static final Long ACTOR_ID = 1L;

    @Test
    void findAll(){
        List<ActorReadDto> result = actorService.findAll();
        assertThat(result).hasSize(0);
    }

    @Test
    void findById(){
        Optional<ActorReadDto> maybeActor = actorService.findById(ACTOR_ID);
        assertThat(maybeActor.isPresent());
        maybeActor.ifPresent(actor -> assertEquals("Nigel", actor.getName()));
    }

    @Test
    void create(){
        ActorCreateEditDto actorCreateEditDto = new ActorCreateEditDto(
                "Piers",
                "Morgan",
                LocalDate.of(1976, 11,11)
        );
         ActorReadDto actualResult = actorService.create(actorCreateEditDto);
         assertEquals(actorCreateEditDto.getName(), actualResult.getName());
         assertEquals(actorCreateEditDto.getSurname(), actualResult.getSurname());
         assertEquals(actorCreateEditDto.getBirthDate(), actualResult.getBirthDate());
    }

    @Test
    void update(){
        ActorCreateEditDto actorCreateEditDto = new ActorCreateEditDto(
                "Piers",
                "Morgan",
                LocalDate.of(1976, 11,11)
        );
        Optional <ActorReadDto> actualResult = actorService.update(ACTOR_ID, actorCreateEditDto);
        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(actor -> {
            assertEquals(actorCreateEditDto.getName(), actor.getName());
            assertEquals(actorCreateEditDto.getSurname(), actor.getSurname());
            assertEquals(actorCreateEditDto.getBirthDate(), actor.getBirthDate());
        });
    }

    @Test
    void delete(){
        assertFalse(actorService.delete(123L));
        assertTrue(actorService.delete(ACTOR_ID));
    }
}
