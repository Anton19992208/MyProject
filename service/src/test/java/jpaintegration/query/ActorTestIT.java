package jpaintegration.query;

import annotation.IT;
import com.example.spring.entity.Actor;
import com.example.spring.entity.Movie;
import com.example.spring.repository.ActorRepository;
import jpaintegration.util.DataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class ActorTestIT {

    private final ActorRepository actorRepository;
    private final EntityManager entityManager;

    @Test
    void findAllActors() {
        DataImporter.importData(entityManager);
        List<Actor> actors = actorRepository.findAll();
        assertThat(actors).hasSize(3);

        List<String> actorNames = actors.stream().map(Actor::getName).collect(toList());
        assertThat(actorNames).containsExactlyInAnyOrder("Nikita", "Petr", "Ivan");
    }

    @Test
    void findAllActorsByMovieName() {
        DataImporter.importData(entityManager);
        List<Actor> result = actorRepository.findActorByMovieName("KingdomOfHeaven");
        assertThat(result).hasSize(1);

        List<String> actorNames = result.stream().map(Actor::getName).collect(toList());
        assertThat(actorNames).containsExactlyInAnyOrder("Nikita");
    }

    @Test
    void findActorBySurname() {
        DataImporter.importData(entityManager);
        var actor = actorRepository.findBySurname("Gurinovich");
        assertThat(actor).isNotNull();
    }
}
