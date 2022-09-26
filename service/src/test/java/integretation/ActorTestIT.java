package integretation;

import com.example.entity.Actor;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestUtil;
import static org.assertj.core.api.Assertions.assertThat;

public class ActorTestIT {

    @Test
    void shouldCreateMovie(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();

        session.save(actor);

        session.getTransaction().commit();
        assertThat(actor).isNotNull();
    }

    @Test
    void shouldDeleteMovie(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        session.save(actor);
        Actor actor1 = session.find(Actor.class, actor.getId());

        session.delete(actor1);

        session.getTransaction().commit();
        assertThat(session.find(Actor.class, actor.getId())).isNull();
    }

    @Test
    void shouldUpdateMovie(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        session.save(actor);

        actor.setName("Charles");
        actor.setSurname("Windsor");
        session.flush();
        session.clear();
        Actor actor1 = session.get(Actor.class, 1L);

        session.getTransaction().commit();
        assertThat(actor).isEqualTo(actor1);
    }

    @Test
    void shouldGetActor(){
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Actor actor = TestUtil.getActor();
        session.save(actor);

        Actor actor1 = session.get(Actor.class, 1L);
        assertThat(actor).isEqualTo(actor1);
    }
}
