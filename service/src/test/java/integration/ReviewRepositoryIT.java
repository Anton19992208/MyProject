package integration;

import annotation.IT;
import com.example.spring.entity.Movie;
import com.example.spring.entity.Review;
import com.example.spring.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import utils.TestUtil;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;


@IT
@RequiredArgsConstructor
@Transactional
public class ReviewRepositoryIT {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;

    @Test
    void shouldCreateReview() {
        Review review = TestUtil.getReview();
        Movie movie = TestUtil.getMovie();
        entityManager.persist(movie);
        review.setMovie(movie);

        reviewRepository.save(review);

        assertThat(review.getId()).isNotNull();
    }

    @Test
    void shouldDeleteReview() {
        Review review = TestUtil.getReview();
        reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        var reviewToDelete = entityManager.find(Review.class, review.getId());
        reviewRepository.delete(reviewToDelete);

        assertThat(entityManager.find(Review.class, review.getId())).isNull();
    }

    @Test
    void shouldUpdateReview() {
        Review review = TestUtil.getReview();
        Movie movie = TestUtil.getMovie();
        Movie newMovie = TestUtil.getNewMovie();
        entityManager.persist(movie);
        entityManager.persist(newMovie);
        review.setMovie(movie);
        reviewRepository.save(review);

        review.setGrade(7);
        review.setMovie(newMovie);
        reviewRepository.update(review);
        entityManager.flush();
        entityManager.clear();

        var updatedReview = entityManager.find(Review.class, review.getId());
        assertThat(updatedReview.getId()).isEqualTo(review.getId());
    }

    @Test
    void shouldFindReviewById() {
        Review actualReview = TestUtil.getReview();
        reviewRepository.save(actualReview);
        entityManager.flush();
        entityManager.clear();

        Optional<Review> expectedReview = reviewRepository.findById(actualReview.getId());

        assertThat(expectedReview.get().getId()).isEqualTo(actualReview.getId());
    }

    @Test
    void shouldFindAllReviews() {
        Review review = TestUtil.getReview();
        Review newReview = TestUtil.getNewReview();
        reviewRepository.save(review);
        reviewRepository.save(newReview);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(2);

        List<Integer> reviewGrades = reviews.stream().map(Review::getGrade).collect(toList());
        assertThat(reviewGrades).containsExactlyInAnyOrder(9, 8);
    }
}
