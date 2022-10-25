package springTest.ITTests;

import com.example.dao.ActorRepository;
import com.example.dao.MovieActorRepository;
import com.example.dao.MovieRepository;
import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import springTest.configuration.ApplicationContext;
import util.TestUtil;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ActorTestIT {

    private static final MovieRepository movieRepository = ApplicationContext.getMovieRepository();
    private static final ActorRepository actorRepository = ApplicationContext.getActorRepository();
    private static final MovieActorRepository movieActorRepository = ApplicationContext.getMovieActorRepository();
    private static final Session session = (Session) ApplicationContext.getEntityManager();

    @Test
    void shouldCreateActor() {
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        movieRepository.save(movie);
        actorRepository.save(actor);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        actorRepository.save(actor);

        assertThat(actor.getId()).isNotNull();

    }

    @Test
    void shouldDeleteActor() {
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        actorRepository.save(actor);
        session.flush();
        session.clear();

        var actorToDelete = session.find(Actor.class, actor.getId());
        actorRepository.delete(actorToDelete);

        assertThat(session.find(Actor.class, actor.getId())).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateActor() {
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        Movie newMovie = TestUtil.getNewMovie();
        Movie movie = TestUtil.getMovie();
        MovieActor movieActor = TestUtil.getMovieActor();
        movieRepository.save(movie);
        movieRepository.save(newMovie);
        actorRepository.save(actor);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);
        movieActorRepository.save(movieActor);

        actor.setName("Adam");
        movieActor.setMovie(newMovie);
        actorRepository.update(actor);
        session.flush();
        session.clear();

        var updatedActor = session.get(Actor.class, actor.getId());
        assertThat(updatedActor.getId()).isEqualTo(actor.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindById(){
        session.beginTransaction();
        Actor actualActor = TestUtil.getActor();
        actorRepository.save(actualActor);
        session.flush();
        session.clear();

        Optional<Actor> expectedActor = actorRepository.findById(actualActor.getId());

        assertThat(expectedActor.get().getId()).isEqualTo(actualActor.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindAllActors() {
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        Actor newActor = TestUtil.getNewActor();
        actorRepository.save(actor);
        actorRepository.save(newActor);
        session.flush();
        session.clear();

        List<Actor> actors = actorRepository.findAll();
        assertThat(actors).hasSize(2);

        List<String> actorNames = actors.stream().map(Actor::getName).collect(toList());
        assertThat(actorNames).containsExactlyInAnyOrder("Robert", "Grom");
        session.getTransaction().rollback();
    }
}
