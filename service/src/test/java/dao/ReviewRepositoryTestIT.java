package dao;

import com.example.dao.MovieRepository;
import com.example.dao.ReviewRepository;
import com.example.entity.Movie;
import com.example.entity.Review;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.ProxySession;
import util.TestUtil;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.as;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(PER_METHOD)
public class ReviewRepositoryTestIT extends ProxySession {

    private final Session session = createProxySession(sessionFactory);
    private final ReviewRepository reviewRepository = new ReviewRepository(session);
    private final MovieRepository movieRepository = new MovieRepository(session);

    @Test
    void shouldCreateReview(){
        session.beginTransaction();
        Review review = TestUtil.getReview();
        Movie movie = TestUtil.getMovie();
        movieRepository.save(movie);
        review.setMovie(movie);

        reviewRepository.save(review);

        assertThat(review.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteReview(){
        session.beginTransaction();
        Review review = TestUtil.getReview();
        reviewRepository.save(review);
        session.flush();
        session.clear();

        var reviewToDelete = session.get(Review.class, review.getId());
        reviewRepository.delete(reviewToDelete.getId());

        assertThat(session.find(Review.class, review.getId())).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateReview(){
        session.beginTransaction();
        Review review = TestUtil.getReview();
        Movie movie = TestUtil.getMovie();
        Movie newMovie = TestUtil.getNewMovie();
        movieRepository.save(movie);
        movieRepository.save(newMovie);
        review.setMovie(movie);
        reviewRepository.save(review);

        review.setGrade(7);
        review.setMovie(newMovie);
        reviewRepository.update(review);
        session.flush();
        session.clear();

        var updatedReview = session.get(Review.class, review.getId());
        assertThat(updatedReview.getId()).isEqualTo(review.getId());
        session.getTransaction().rollback();

    }

    @Test
    void shouldFindReviewById(){
        session.beginTransaction();
        Review actualReview = TestUtil.getReview();
        reviewRepository.save(actualReview);
        session.flush();
        session.clear();

        Optional<Review> expectedReview = reviewRepository.findById(actualReview.getId());

        assertThat(expectedReview.get().getId()).isEqualTo(actualReview.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindAllReviews(){
        session.beginTransaction();
        Review review = TestUtil.getReview();
        Review newReview = TestUtil.getNewReview();
        reviewRepository.save(review);
        reviewRepository.save(newReview);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(2);

        List<Integer> reviewGrades = reviews.stream().map(Review::getGrade).collect(toList());
        assertThat(reviewGrades).containsExactlyInAnyOrder(9 ,8);
        session.getTransaction().rollback();
    }
}







