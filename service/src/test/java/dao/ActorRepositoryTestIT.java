package dao;

import com.example.dao.ActorRepository;
import com.example.dao.MovieActorRepository;
import com.example.dao.MovieRepository;
import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.ProxySession;
import util.TestUtil;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

@TestInstance(PER_METHOD)
public class ActorRepositoryTestIT extends ProxySession {

    private final Session session = createProxySession(sessionFactory);
    private final MovieRepository movieRepository = new MovieRepository(session);
    private final ActorRepository actorRepository = new ActorRepository(session);
    private final MovieActorRepository movieActorRepository = new MovieActorRepository(session);

    @Test
    void shouldCreateActor() {
//        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        movieRepository.save(movie);
        actorRepository.save(actor);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        actorRepository.save(actor);

        assertThat(actor.getId()).isNotNull();
        session.getTransaction().rollback();
//        Падают два теста(первый и псоледний) если я оставляю session.beginTransaction(), даже не знаю
//         что это может быть, потому что в других тестах всё работает.
    }

    @Test
    void shouldDeleteActor() {
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        actorRepository.save(actor);
        session.flush();
        session.clear();

        var actorToDelete = session.find(Actor.class, actor.getId());
        actorRepository.delete(actorToDelete.getId());

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
    }

    @Test
    void shouldFindById() {
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
