package integretation;

import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import com.example.entity.Review;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestUtil;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;


@TestInstance(PER_METHOD)
public class MovieTestIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    @Test
    void shouldCreateMovie() {
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
    void shouldDeleteMovie() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        session.save(movie);
        session.flush();
        session.clear();

        Movie movieToDelete = session.get(Movie.class, movie.getId());
        session.delete(movieToDelete);

        assertThat(movieToDelete.getId()).isNull();
        session.getTransaction().rollback();

    }

    @Test
    void shouldUpdateMovie() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        Actor newActor = TestUtil.getNewActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        session.save(newActor);
        session.save(movie);
        session.save(actor);
        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        session.save(movieActor);

        movie.setName("PulpFiction2");
        movie.setCountry("UN");
        movieActor.setActor(newActor);
        session.update(movie);
        session.flush();
        session.clear();
        Movie updatedMovie = session.get(Movie.class, movie.getId());

        assertThat(movie.getId()).isEqualTo(updatedMovie.getId());
        session.getTransaction().rollback();

    }

    @Test
    void shouldGetMovie() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        session.save(movie);
        session.flush();
        session.clear();

        var movieAfterGet = session.get(Movie.class, movie.getId());

        assertThat(movie.getId()).isEqualTo(movieAfterGet.getId());
        session.getTransaction().rollback();

    }
}
