package integretation;

import com.example.dao.MovieDao;
import com.example.dao.MovieDaoDsl;
import com.example.dto.MovieDto;
import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import com.example.entity.Review;
import com.querydsl.core.Tuple;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestDataImporter;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class MovieDaoDslTestIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final MovieDaoDsl movieDaoDsl = MovieDaoDsl.getInstance();

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

        List<Movie> results = movieDaoDsl.findAll(session);
        assertThat(results).hasSize(3);

        List<String> names = results.stream().map(Movie::name).collect(toList());
        assertThat(names).containsExactlyInAnyOrder("KingdomOfHeaven", "SinCity", "RussianMovie");

        session.getTransaction().commit();
    }

    @Test
    void findByName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Movie> results = movieDaoDsl.findByName(session, "SinCity");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).name()).isEqualTo("SinCity");

        session.getTransaction().commit();
    }

    @Test
    void findLimitedMoviesOrderedByReleaseDate() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 2;
        List<Movie> results = movieDaoDsl.findLimitedMoviesOrderedByReleaseDate(session, limit);
        assertThat(results).hasSize(limit);

        List<String> names = results.stream().map(Movie::name).collect(toList());
        assertThat(names).contains("RussianMovie", "KingdomOfHeaven");

        session.getTransaction().commit();
    }

    @Test
    void findAllActorsByMovieName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Actor> results = movieDaoDsl.findAllActorsByMovieName(session, "LOK");
        assertThat(results).hasSize(1);

        List<String> names = results.stream().map(Actor::name).collect(toList());
        assertThat(names).containsExactlyInAnyOrder("Anton Kabernik");

        session.getTransaction().commit();
/*        Денис привет, этот тест я не смог сделать, пробовал многи варианты через <Movie>, <MovieActor>
          Комбинировал их но ничего не выходит(Делал это в DAO). Все ломается когда проверяется размер, expected = 1 but actual = 0
          Может ты поможешь? (В тесте DSL таже ситуация)*/

    }

    @Test
    void findAllPaymentsByCompanyName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Review> reviews = movieDaoDsl.findAllReviewByMovieName(session, "KingdomOfHeaven");
        assertThat(reviews).hasSize(5);

        List<Integer> grades = reviews.stream().map(Review::getGrade).collect(toList());
        assertThat(grades).contains(10, 10, 10, 10, 10);

        session.getTransaction().commit();
    }

    @Test
    void findAverageGradeByMovieName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Double averageGrade = movieDaoDsl.findAverageGradeByMovieName(session, "KingdomOfHeaven", "1");
        assertThat(averageGrade).isEqualTo(10);

        session.getTransaction().commit();
    }

    @Test
    void findMovieNameByAvgGradeOrderedByMovieName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<com.querydsl.core.Tuple> results = movieDaoDsl.findMovieNameByAvgGradeOrderedByMovieName(session);
        assertThat(results).hasSize(3);

        List<String> movNames = results.stream().map(it -> it.get(0, String.class)).collect(toList());
        assertThat(movNames).contains("KingdomOfHeaven", "SinCity", "RussianMovie");

        List<Double> orgAvgPayments = results.stream().map(it -> it.get(1, Double.class)).collect(toList());
        assertThat(orgAvgPayments).contains(10.0, 9.0, 3.0);

        session.getTransaction().commit();
    }

    @Test
    void findSetOfMoviesWhereAvgGradeIsMoreThenAvgGradeOfAllMoviesOrderedByName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Tuple> results = movieDaoDsl.findSetOfMoviesWhereAvgGradeIsMoreThenAvgGradeOfAllMoviesOrderedByName(session);
        assertThat(results).hasSize(2);

        List<String> names = results.stream().map(r -> r.get(0, Movie.class).name()).collect(toList());
        assertThat(names).contains("KingdomOfHeaven", "SinCity");


        List<Double> averageGrades = results.stream().map(r -> r.get(1, Double.class)).collect(toList());
        assertThat(averageGrades).contains(10.0, 9.0);

        session.getTransaction().commit();
    }

}
