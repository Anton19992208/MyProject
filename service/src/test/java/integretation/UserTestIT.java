package integretation;

import com.example.entity.User;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestIT {

    @Test
    void checkH2(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();

        User user = TestUtil.getUser();

        session.save(user);
        session.getTransaction().commit();
    }

    @Test
    void shouldSaveUser(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();

        session.save(user);

        session.getTransaction().commit();
        assertThat(user).isNotNull();
    }

    @Test
    void shouldDeleteUser(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        session.save(user);
        User user1 = session.find(User.class, 1L);

        session.delete(user1);

        session.getTransaction().commit();
        assertThat(session.find(User.class, user1.getId())).isNull();
    }

    @Test
    void shouldUpdateUser(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        session.save(user);

        user.setEmail("@2343com");
        user.setName("Jorge");
        session.save(user);
        session.flush();
        session.clear();
        User user1 = session.get(User.class, 1L);

        session.getTransaction().commit();
        assertThat(user1).isEqualTo(user);
    }

    @Test
    void shouldGetUser(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();
        session.beginTransaction();
        User user = TestUtil.getUser();
        session.save(user);

        var user1 = session.get(User.class,1L);

        session.getTransaction().commit();
        assertThat(user1).isEqualTo(user);
    }
}
