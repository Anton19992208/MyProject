package integretation;

import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import lombok.Cleanup;
import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestUtil;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
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

        actor.setName("Charles");
        actor.setSurname("Windsor");
        movieActor.setMovie(newMovie);
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