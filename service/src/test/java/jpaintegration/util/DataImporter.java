package jpaintegration.util;

import com.example.spring.entity.Actor;
import com.example.spring.entity.Movie;
import com.example.spring.entity.MovieActor;
import com.example.spring.entity.Review;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@UtilityClass
public class DataImporter {

    public void importData(EntityManager entityManager) {

        Actor nik = saveActor(entityManager, "Nikita", "Gurinovich",
                LocalDate.of(1999, 2, 8));
        Actor ivan = saveActor(entityManager, "Petr", "Ivanov",
                LocalDate.of(1969, 6, 8));
        Actor petr = saveActor(entityManager, "Ivan", "Petrov",
                LocalDate.of(1949, 4, 8));

        Movie kingdomOfHeaven = saveMovie(entityManager, "KingdomOfHeaven",
                LocalDate.of(1993, 12, 12), "1");
        Movie sinCity = saveMovie(entityManager, "SinCity",
                LocalDate.of(1994, 11, 11), "2");
        Movie russianMovie = saveMovie(entityManager, "RussianMovie",
                LocalDate.of(1990, 10, 10), "3");

        MovieActor movieActor1 = saveMovieActor(entityManager, nik, kingdomOfHeaven);
        MovieActor movieActor2 = saveMovieActor(entityManager, ivan, sinCity);
        MovieActor movieActor3 = saveMovieActor(entityManager, petr, russianMovie);


        User bernySanders = saveUser(entityManager, "Berny", "Sanders",
                Role.ADMIN, "4747474", "@berny.com");
        User nigelFarage = saveUser(entityManager, "Nigel", "Farage",
                Role.CLIENT, "56757578", "@nigel.com");
        User pierceMorgan = saveUser(entityManager, "Pierce", "Morgan",
                Role.CLIENT, "585858", "@pierce.com");
        User borisJonson = saveUser(entityManager, "Boris", "Jonson",
                Role.CLIENT, "48438484", "@boris.com");
        User robertRobin = saveUser(entityManager, "Robert", "Robin",
                Role.CLIENT, "1213133", "@robert.com");

        saveReview(entityManager, "Marvelous", 10, bernySanders, kingdomOfHeaven);
        saveReview(entityManager, "Amazing", 10, nigelFarage, kingdomOfHeaven);
        saveReview(entityManager, "Prodigious", 10, pierceMorgan, kingdomOfHeaven);
        saveReview(entityManager, "Impeccable", 10, borisJonson, kingdomOfHeaven);
        saveReview(entityManager, "Immaculate", 10, robertRobin, kingdomOfHeaven);

        saveReview(entityManager, "Perfect", 9, nigelFarage, sinCity);
        saveReview(entityManager, "Stupendous", 9, bernySanders, sinCity);
        saveReview(entityManager, "Great", 9, pierceMorgan, sinCity);
        saveReview(entityManager, "Astonishing", 9, borisJonson, sinCity);
        saveReview(entityManager, "Magnificent", 9, robertRobin, sinCity);

        saveReview(entityManager, "Just Sucks", 5, bernySanders, russianMovie);
        saveReview(entityManager, "rubbish", 2, nigelFarage, russianMovie);
        saveReview(entityManager, "awful", 1, pierceMorgan, russianMovie);
        saveReview(entityManager, "appalling", 3, borisJonson, russianMovie);
        saveReview(entityManager, "abomination", 4, robertRobin, russianMovie);

    }

    private Movie saveMovie(EntityManager entityManager,
                            String name,
                            LocalDate releaseDate,
                            String producer) {
        Movie movie = Movie.builder()
                .name(name)
                .producer(producer)
                .releaseDate(releaseDate)
                .build();
        entityManager.persist(movie);
        return movie;
    }

    private Actor saveActor(EntityManager entityManager,
                            String name,
                            String surname,
                            LocalDate birthday) {
        Actor actor = Actor.builder()
                .name(name)
                .surname(surname)
                .birthdate(birthday)
                .build();

        entityManager.persist(actor);
        return actor;

    }

    private User saveUser(EntityManager entityManager,
                          String name,
                          String surname,
                          Role role,
                          String password,
                          String email) {
        User user = User.builder()
                .name(name)
                .surname(surname)
                .role(role)
                .password(password)
                .email(email)
                .build();
        entityManager.persist(user);
        return user;
    }

    private Review saveReview(EntityManager entityManager,
                              String text,
                              double grade,
                              User user,
                              Movie movie) {
        Review review = Review.builder()
                .text(text)
                .grade(grade)
                .user(user)
                .movie(movie)
                .build();
        entityManager.persist(review);
        return review;
    }

    private MovieActor saveMovieActor(EntityManager entityManager, Actor actor, Movie movie) {
        MovieActor movieActor = MovieActor.builder()
                .actor(actor)
                .movie(movie)
                .build();
        entityManager.persist(movieActor);
        return movieActor;
    }
}
