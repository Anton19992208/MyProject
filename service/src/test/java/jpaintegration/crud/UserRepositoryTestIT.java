package jpaintegration.crud;

import annotation.IT;
import com.example.spring.entity.Review;
import com.example.spring.entity.User;
import com.example.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import utils.TestUtil;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;


@IT
@RequiredArgsConstructor
public class UserRepositoryTestIT {

    private final EntityManager entityManager;
    private final UserRepository userRepository;


    @Test
    void shouldCreateUser() {
        User user = TestUtil.getUser();
        Review review = TestUtil.getReview();
        entityManager.persist(review);
        user.addReview(review);

        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void shouldDeleteUser() {
        User user = TestUtil.getUser();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        var userToDelete = entityManager.find(User.class, user.getId());
        userRepository.delete(userToDelete);

        assertThat(entityManager.find(User.class, user.getId())).isNull();
    }

    @Test
    void shouldUpdateUser() {
        User user = TestUtil.getUser();
        Review review = TestUtil.getReview();
        Review newReview = TestUtil.getNewReview();
        entityManager.persist(review);
        user.addReview(review);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        user.setName("Boris");
        user.addReview(newReview);
        userRepository.saveAndFlush(user);

        var updatedMovie = entityManager.find(User.class, user.getId());
        assertThat(updatedMovie.getId()).isEqualTo(user.getId());
    }

    @Test
    void shouldFindUserById() {
        User actualUser = TestUtil.getUser();
        entityManager.persist(actualUser);
        entityManager.flush();
        entityManager.clear();

        Optional<User> expectedUser = userRepository.findById(actualUser.getId());

        assertThat(expectedUser.get().getId()).isEqualTo(actualUser.getId());
    }

    @Test
    void shouldFindAllUsers() {
        User user = TestUtil.getUser();
        User newUser = TestUtil.getNewUser();
        entityManager.persist(user);
        entityManager.persist(newUser);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);

        List<String> userNames = users.stream().map(User::getName).collect(toList());
        assertThat(userNames).containsExactlyInAnyOrder("James", "Joe");
    }
}
