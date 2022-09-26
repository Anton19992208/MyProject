package integretation;

import com.example.entity.Review;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestUtil;
import static org.assertj.core.api.Assertions.assertThat;

public class ReviewTestIT {

    @Test
    void shouldCreateReview(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();

        session.save(review);

        session.getTransaction().commit();
        assertThat(review).isNotNull();
    }

    @Test
    void shouldDeleteReview(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        session.save(review);
        Review review1 = session.find(Review.class, review.getId());

        session.delete(review1);

        session.getTransaction().commit();
        assertThat(session.find(Review.class, review.getId())).isNull();
    }

    @Test
    void shouldUpdateReview(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        session.save(review);

        review.setGrade(7);
        session.update(review);
        session.flush();
        session.clear();
        Review review1 = session.get(Review.class, 1L);

        session.getTransaction().commit();
        assertThat(review).isEqualTo(review1);
    }

    @Test
    void shouldGetReview(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        Review review = TestUtil.getReview();
        session.save(review);

        Review review1 = session.get(Review.class, 1L);

        session.getTransaction().commit();
        assertThat(review).isEqualTo(review1);

    }
}
