package springTest.ITTests;

import com.example.dao.ReviewRepository;
import com.example.dao.UserRepository;
import com.example.entity.Review;
import com.example.entity.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import springTest.configuration.ApplicationContext;
import util.TestUtil;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestIT {

    public static final UserRepository userRepository = ApplicationContext.getUserRepository();
    public static final ReviewRepository reviewRepository = ApplicationContext.getReviewRepository();
    public static final Session session = (Session) ApplicationContext.getEntityManager();

    @Test
    void shouldCreateUser() {
        session.beginTransaction();
        User user = TestUtil.getUser();
        Review review = TestUtil.getReview();
        reviewRepository.save(review);
        user.addReview(review);

        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteUser() {
        session.beginTransaction();
        User user = TestUtil.getUser();
        userRepository.save(user);
        session.flush();
        session.clear();

        userRepository.delete(user);

        assertThat(session.find(User.class, user.getId())).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateUser() {
        session.beginTransaction();
        User user = TestUtil.getUser();
        Review review = TestUtil.getReview();
        Review newReview = TestUtil.getNewReview();
        reviewRepository.save(review);
        user.addReview(review);
        userRepository.save(user);
        session.flush();
        session.clear();

        user.setName("Boris");
        user.addReview(newReview);
        userRepository.update(user);

        var updatedMovie = session.get(User.class, user.getId());
        assertThat(updatedMovie.getId()).isEqualTo(user.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindUserById(){
        session.beginTransaction();
        User actualUser = TestUtil.getUser();
        userRepository.save(actualUser);
        session.flush();
        session.clear();

        Optional<User> expectedUser = userRepository.findById(actualUser.getId());

        assertThat(expectedUser.get().getId()).isEqualTo(actualUser.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindAllUsers(){
        session.beginTransaction();
        User user1 = TestUtil.getUser();
        User user2 = TestUtil.getUser();
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);

        List<String> userNames = users.stream().map(User::getName).collect(toList());
        assertThat(userNames).containsExactlyInAnyOrder("James", "James");
        session.getTransaction().rollback();
    }

}
