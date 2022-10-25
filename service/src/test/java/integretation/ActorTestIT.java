package integretation;

import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

@TestInstance(PER_METHOD)
public class ActorTestIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();


    @Test
    void shouldCreateMovieWithActor() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        session.save(actor);
        session.save(movie);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        session.save(movieActor);

        assertThat(movieActor.getId()).isNotNull();
        session.getTransaction().rollback();

    }

    @Test
    void shouldDeleteActor() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        session.save(actor);
        session.clear();
        session.flush();

        Actor actor1 = session.find(Actor.class, actor.getId());
        session.delete(actor1);

        assertThat(session.find(Actor.class, actor.getId())).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateActor() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        Movie movie = TestUtil.getMovie();
        Movie newMovie = TestUtil.getNewMovie();
        MovieActor movieActor = TestUtil.getMovieActor();
        session.save(newMovie);
        session.save(movie);
        session.save(actor);
        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        session.save(movieActor);

        actor.setName("Charles");
        actor.setSurname("Windsor");
        movieActor.setMovie(newMovie);
        session.update(actor);
        session.flush();
        session.clear();
        Actor actor1 = session.get(Actor.class, actor.getId());

        assertThat(actor.getId()).isEqualTo(actor1.getId());
        session.getTransaction().rollback();

    }

    @Test
    void shouldGetActor() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        session.save(actor);
        session.flush();
        session.clear();

        var actor1 = session.get(Actor.class, actor.getId());
        assertThat(actor).isEqualTo(actor1);

        session.getTransaction().rollback();

    }

}