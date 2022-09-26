package integretation;

import com.example.entity.Movie;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieTestIT {

    @Test
    void shouldCreateMovie(){

        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
//        Actor actor = TestUtil.getActor();
//        movie.addActor(actor);
        session.save(movie);
//        User user = TestUtil.getUser();
//        Review review = TestUtil.getReview();
//        review.setUser(user);
//        session.save(review);

        session.getTransaction().commit();

        assertThat(movie).isNotNull();
    }

    @Test
    void shouldDeleteMovie(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        session.save(movie);
        Movie movie1 = session.find(Movie.class, 1L);

        session.delete(movie1);
        session.getTransaction().commit();

        assertThat(session.find(Movie.class, movie1.getId())).isNull();
    }

    @Test
    void shouldUpdateMovie(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        session.save(movie);

        movie.setName("PulpFiction2");
        movie.setCountry("UN");
        session.update(movie);
        session.flush();
        session.clear();
        Movie movie1 = session.find(Movie.class, movie.getId());

        session.getTransaction().commit();
        assertThat(movie).isEqualTo(movie1);
    }

    @Test
    void shouldGetMovie(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        session.save(movie);

        var movieAfterGet = session.get(Movie.class, 1L);

        session.getTransaction().commit();
        assertThat(movie).isEqualTo(movieAfterGet);
    }
}
