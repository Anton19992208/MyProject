package com.example.entity;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);

        @Cleanup SessionFactory sessionFactory = configuration.buildSessionFactory();
        @Cleanup Session session =  sessionFactory.openSession();

        session.beginTransaction();

        Movie movie = Movie.builder()
                .name("Pulp Fiction4132")
                .country("USA")
                .producer("Tarantino")
                .releaseDate(new ReleaseDate(LocalDate.of(1999,12,12)))
                .genre(Genre.SCIENCE_FICTION)
                .build();

        Actor actor = Actor.builder()
                .name("Rob1232")
                .surname("Stark")
                .birthdate(new Birthday(LocalDate.of(1998,11,11)))
                .build();
        session.clear();

        movie.addActor(actor);
        session.save(movie);

        User user = User.builder()
                .name("petr")
                .surname("petrov")
                .email("@ru7440")
                .password("00230433")
                .build();

        Review review = Review.builder()
                .grade(2)
                .text("marvelous")
                .movie(movie)
                .user(user)
                .build();

        review.setUser(user);

        session.save(user);

//        session.save(movie);
//        session.save(actor);
//
//        session.evict(actor);

        session.getTransaction().commit();
    }
}
