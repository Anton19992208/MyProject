package integretation;

import com.example.dto.MovieDto;
import com.example.dao.MovieDao;
import com.example.entity.Actor;
import com.example.entity.Genre;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import com.example.entity.Review;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestDataImporter;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

@TestInstance(PER_METHOD)
public class MovieDaoTestIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final MovieDao movieDao = MovieDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Movie> results = movieDao.findAll(session);
        assertThat(results).hasSize(3);

        List<String> names = results.stream().map(Movie::name).collect(toList());
        assertThat(names).containsExactlyInAnyOrder("KingdomOfHeaven", "SinCity", "RussianMovie");

        session.getTransaction().commit();
    }

    @Test
    void findByName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Movie> results = movieDao.findByName(session, "SinCity");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).name()).isEqualTo("SinCity");

        session.getTransaction().commit();
    }

    @Test
    void findLimitedMoviesOrderedByReleaseDate() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 2;
        List<Movie> results = movieDao.findLimitedMoviesOrderedByReleaseDate(session, limit);
        assertThat(results).hasSize(limit);

        List<String> names = results.stream().map(Movie::name).collect(toList());
        assertThat(names).contains("RussianMovie", "KingdomOfHeaven");

        session.getTransaction().commit();
    }
    @Test
    void findAllActorsByMovieName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();


        List<Actor> results = movieDao.findAllActorsByMovieName(session, "KingdomOfHeaven");
        assertThat(results).hasSize(1);

        List<String> names = results.stream().map(Actor::getName).collect(toList());

        assertThat(names).containsExactlyInAnyOrder("Nikita");

        session.getTransaction().commit();
    }

    @Test
    void findAllPaymentsByCompanyName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Review> reviews = movieDao.findAllReviewByMovieName(session, "KingdomOfHeaven");
        assertThat(reviews).hasSize(5);

        List<Integer> grades = reviews.stream().map(Review::getGrade).collect(toList());
        assertThat(grades).contains(10, 10, 10, 10, 10);

        session.getTransaction().commit();
    }

    @Test
    void findAverageGradeByMovieName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Double averageGrade = movieDao.findAverageGradeByMovieName(session, "KingdomOfHeaven", "1");
        assertThat(averageGrade).isEqualTo(10);

        session.getTransaction().commit();
    }

    @Test
    void findMovieNameByAvgGradeOrderedByMovieName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<MovieDto> results = movieDao.findMovieNameByAvgGradeOrderedByMovieName(session);
        assertThat(results).hasSize(3);

        List<String> movNames = results.stream().map(MovieDto::getName).collect(toList());
        assertThat(movNames).contains("KingdomOfHeaven", "SinCity", "RussianMovie");

        List<Double> orgAvgPayments = results.stream().map(MovieDto::getGrade).collect(toList());
        assertThat(orgAvgPayments).contains(10.0, 9.0, 3.0);

        session.getTransaction().commit();
    }
    @Test
    void findSetOfMoviesWhereAvgGradeIsMoreThenAvgGradeOfAllMoviesOrderedByName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Tuple> results = movieDao.findSetOfMoviesWhereAvgGradeIsMoreThenAvgGradeOfAllMoviesOrderedByName(session);
        assertThat(results).hasSize(2);

        List<String> names = results.stream().map(r -> r.get(0, Movie.class).name()).collect(toList());
        assertThat(names).contains("KingdomOfHeaven", "SinCity");


        List<Double> averageGrades = results.stream().map(r -> r.get(1, Double.class)).collect(toList());
        assertThat(averageGrades).contains(10.0, 9.0);

        session.getTransaction().commit();
    }

}
