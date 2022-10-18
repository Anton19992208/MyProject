package util;

import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import com.example.entity.Review;
import com.example.entity.Role;
import com.example.entity.User;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testcontainers.shaded.org.bouncycastle.est.jcajce.JsseESTServiceBuilder;

import java.time.LocalDate;
import java.util.List;


@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Actor nik = saveActor(session, "Nikita", "Gurinovich",
                LocalDate.of(1999, 2, 8));
        Actor ivan = saveActor(session, "Petr", "Ivanov",
                LocalDate.of(1969, 6, 8));
        Actor petr = saveActor(session, "Petr", "Petrov",
                LocalDate.of(1949, 4, 8));

        Movie kingdomOfHeaven = saveMovie(session, "KingdomOfHeaven",
                LocalDate.of(1993, 12, 12), "1");
        Movie sinCity = saveMovie(session, "SinCity",
                LocalDate.of(1994, 11, 11), "2");
        Movie russianMovie = saveMovie(session, "RussianMovie",
                LocalDate.of(1990, 10, 10),  "3");

        MovieActor movieActor1 = saveMovieActor(session, nik, kingdomOfHeaven);


        User bernySanders = saveUser(session, "Berny", "Sanders",
                Role.ADMIN, "4747474", "@berny.com");
        User nigelFarage = saveUser(session, "Nigel", "Farage",
                Role.CLIENT, "56757578", "@nigel.com");
        User pierceMorgan = saveUser(session, "Pierce", "Morgan",
                Role.CLIENT, "585858", "@pierce.com");
        User borisJonson = saveUser(session, "Boris", "Jonson",
                Role.CLIENT, "48438484", "@boris.com");
        User robertRobin = saveUser(session, "Robert", "Robin",
                Role.CLIENT, "1213133", "@robert.com");

        saveReview(session, "Marvelous", 10, bernySanders, kingdomOfHeaven);
        saveReview(session, "Amazing", 10, nigelFarage, kingdomOfHeaven);
        saveReview(session, "Prodigious", 10, pierceMorgan, kingdomOfHeaven);
        saveReview(session, "Impeccable", 10, borisJonson, kingdomOfHeaven);
        saveReview(session, "Immaculate", 10, robertRobin, kingdomOfHeaven);

        saveReview(session, "Stupendous", 9, bernySanders, sinCity);
        saveReview(session, "Perfect", 9, nigelFarage, sinCity);
        saveReview(session, "Great", 9, pierceMorgan, sinCity);
        saveReview(session, "Astonishing", 9, borisJonson, sinCity);
        saveReview(session, "Magnificent", 9, robertRobin, sinCity);

        saveReview(session, "Just Sucks", 5, bernySanders, russianMovie);
        saveReview(session, "rubbish", 2, nigelFarage, russianMovie);
        saveReview(session, "awful", 1, pierceMorgan, russianMovie);
        saveReview(session, "appalling", 3, borisJonson, russianMovie);
        saveReview(session, "abomination", 4, robertRobin, russianMovie);


    }

    private Movie saveMovie(Session session,
                            String name,
                            LocalDate releaseDate,
                            String producer) {
        Movie movie = Movie.builder()
                .name(name)
                .producer(producer)
                .releaseDate(releaseDate)
                .build();
        session.save(movie);
        return movie;
    }

    private Actor saveActor(Session session,
                            String name,
                            String surname,
                            LocalDate birthday) {
        Actor actor = Actor.builder()
                .name(name)
                .surname(surname)
                .birthdate(birthday)
                .build();

        session.save(actor);
        return actor;

    }


    private User saveUser(Session session,
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
        session.save(user);
        return user;
    }

    private Review saveReview(Session session,
                            String text,
                            int grade,
                            User user,
                            Movie movie) {
        Review review = Review.builder()
                .text(text)
                .grade(grade)
                .user(user)
                .movie(movie)
                .build();
        session.save(review);
        return review;
    }
    private MovieActor saveMovieActor(Session session, Actor actor, Movie movie){
        MovieActor movieActor = MovieActor.builder()
                .actor(actor)
                .movie(movie)
                .build();
        session.save(movieActor);
        return movieActor;
    }
}
