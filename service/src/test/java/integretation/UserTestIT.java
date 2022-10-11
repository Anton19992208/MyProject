package integretation;

import com.example.entity.Review;
import com.example.entity.User;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class UserTestIT {
    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    @Test
    void shouldSaveUser() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        Review review = TestUtil.getReview();
        session.save(review);
        user.addReview(review);

        session.save(user);

        assertThat(user).isNotNull();
        session.getTransaction().rollback();

    }

    @Test
    void shouldDeleteUser() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        session.save(user);
        session.flush();
        session.clear();

        User user1 = session.find(User.class, user.getId());
        session.delete(user1);

        assertThat(session.find(User.class, user1.getId())).isNull();
        session.getTransaction().rollback();

    }

    @Test
    void shouldUpdateUser() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        Review review = TestUtil.getReview();
        Review newReview = TestUtil.getNewReview();
        session.save(newReview);
        session.save(review);
        user.addReview(review);
        session.save(user);

        user.setEmail("@2343com");
        user.setName("Jorge");
        user.addReview(newReview);
        session.save(user);
        session.flush();
        session.clear();
        User user1 = session.get(User.class, user.getId());

        assertThat(user1).isEqualTo(user);
        session.getTransaction().rollback();

    }

    @Test
    void shouldGetUser() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        session.save(user);
        session.flush();
        session.clear();

        var user1 = session.get(User.class, user.getId());

        assertThat(user1).isEqualTo(user);
        session.getTransaction().rollback();

    }
}
