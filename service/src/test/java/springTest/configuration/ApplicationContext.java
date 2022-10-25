package springTest.configuration;

import com.example.dao.ActorRepository;
import com.example.dao.MovieActorRepository;
import com.example.dao.MovieRepository;
import com.example.dao.ReviewRepository;
import com.example.dao.UserRepository;
import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

public class ApplicationContext {

    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    private Session session = context.getBean(Session.class);

    public static MovieRepository getMovieRepository() {
        return context.getBean(MovieRepository.class);
    }

    public static EntityManager getEntityManager() {
        return context.getBean(EntityManager.class);
    }

    public static SessionFactory getSessionFactory() {
        return context.getBean(SessionFactory.class);

    }

    public static ActorRepository getActorRepository() {
        return context.getBean(ActorRepository.class);
    }

    public static MovieActorRepository getMovieActorRepository() {
        return context.getBean(MovieActorRepository.class);
    }

    public static UserRepository getUserRepository() {
        return context.getBean(UserRepository.class);
    }

    public static ReviewRepository getReviewRepository() {
        return context.getBean(ReviewRepository.class);
    }
}