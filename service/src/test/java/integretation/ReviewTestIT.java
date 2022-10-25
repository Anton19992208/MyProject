package integretation;

import com.example.entity.Movie;
import com.example.entity.Review;
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
public class ReviewTestIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    @Test
    void shouldCreateReview() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        Movie movie = TestUtil.getMovie();
        session.save(movie);
        session.save(review);
        review.setMovie(movie);
        session.save(review);

        assertThat(review.getId()).isNotNull();

        session.getTransaction().rollback();

    }

    @Test
    void shouldDeleteReview() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        session.save(review);
        session.flush();
        session.clear();

        Review review1 = session.find(Review.class, review.getId());
        session.delete(review1);

        assertThat(session.find(Review.class, review.getId())).isNull();
        session.getTransaction().rollback();

    }

    @Test
    void shouldUpdateReview() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        Movie movie = TestUtil.getMovie();
        Movie newMovie = TestUtil.getNewMovie();
        session.save(movie);
        session.save(newMovie);
        review.setMovie(movie);
        session.save(review);


        review.setGrade(7);
        review.setMovie(newMovie);
        session.update(review);
        session.flush();
        session.clear();
        Review review1 = session.get(Review.class, review.getId());

        assertThat(review.getId()).isEqualTo(review1.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldGetReview() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        session.save(review);
        session.flush();
        session.clear();

        Review review1 = session.get(Review.class, review.getId());

        assertThat(review).isEqualTo(review1);
        session.getTransaction().rollback();


    }
}
