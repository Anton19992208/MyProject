package integration;

import annotation.IT;
import com.example.spring.entity.Actor;
import com.example.spring.entity.Movie;
import com.example.spring.entity.MovieActor;
import com.example.spring.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import utils.TestUtil;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;


@IT
@RequiredArgsConstructor
@Transactional
public class ActorRepositoryIT {

    private final EntityManager entityManager;
    private final ActorRepository actorRepository;

    @Test
    void shouldCreateActor() {
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        entityManager.persist(movie);
        entityManager.persist(actor);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        entityManager.persist(actor);

        assertThat(actor.getId()).isNotNull();

    }

    @Test
    void shouldDeleteActor() {
        Actor actor = TestUtil.getActor();
        entityManager.persist(actor);
        entityManager.flush();
        entityManager.clear();

        var actorToDelete = entityManager.find(Actor.class, actor.getId());
        actorRepository.delete(actorToDelete);

        assertThat(entityManager.find(Actor.class, actor.getId())).isNull();
    }

    @Test
    void shouldUpdateActor() {
        Actor actor = TestUtil.getActor();
        Movie newMovie = TestUtil.getNewMovie();
        Movie movie = TestUtil.getMovie();
        MovieActor movieActor = TestUtil.getMovieActor();
        entityManager.persist(movie);
        entityManager.persist(newMovie);
        entityManager.persist(actor);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);
        entityManager.persist(movieActor);

        actor.setName("Adam");
        movieActor.setMovie(newMovie);
        actorRepository.update(actor);
        entityManager.flush();
        entityManager.clear();

        var updatedActor = entityManager.find(Actor.class, actor.getId());

        assertThat(updatedActor.getId()).isEqualTo(actor.getId());
    }

    @Test
    void shouldFindById(){
        Actor actualActor = TestUtil.getActor();
        actorRepository.save(actualActor);
        entityManager.flush();
        entityManager.clear();

        Optional<Actor> expectedActor = actorRepository.findById(actualActor.getId());

        assertThat(expectedActor.get().getId()).isEqualTo(actualActor.getId());
    }

    @Test
    void shouldFindAllActors() {
        Actor actor = TestUtil.getActor();
        Actor newActor = TestUtil.getNewActor();
        actorRepository.save(actor);
        actorRepository.save(newActor);
        entityManager.flush();
        entityManager.clear();

        List<Actor> actors = actorRepository.findAll();
        assertThat(actors).hasSize(2);

        List<String> actorNames = actors.stream().map(Actor::getName).collect(toList());
        assertThat(actorNames).containsExactlyInAnyOrder("Robert", "Grom");
    }

}
