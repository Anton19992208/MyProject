package utils;

import com.example.spring.entity.Actor;
import com.example.spring.entity.Genre;
import com.example.spring.entity.Movie;
import com.example.spring.entity.MovieActor;
import com.example.spring.entity.Review;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;

import java.time.LocalDate;

public class TestUtil {

    public static User getUser() {
        return User.builder()
                .name("James")
                .surname("O'Brien")
                .email("@com")
                .password("21232420")
                .role(Role.CLIENT)
                .build();
    }

    public static Movie getMovie() {
        return Movie.builder()
                .name("Pulp Fiction")
                .producer("Quentin Tarantino")
                .releaseDate(LocalDate.of(1994, 10, 14))
                .genre(Genre.COMEDY)
                .country("USA")
                .build();
    }

    public static Actor getActor() {
        return Actor.builder()
                .name("Robert")
                .surname("Jonson")
                .birthdate(LocalDate.of(1977, 12, 12))
                .build();
    }

    public static Review getReview() {
        return Review.builder()
                .grade(9)
                .text("Cool")
                .build();
    }

    public static MovieActor getMovieActor() {
        return MovieActor.builder()
                .createdAt(LocalDate.of(2020, 12, 12))
                .createdBy("Karl Smith")
                .build();
    }

    public static Actor getNewActor() {
        return Actor.builder()
                .name("Grom")
                .surname("Tor")
                .birthdate(LocalDate.of(1999, 1, 1))
                .build();
    }

    public static Movie getNewMovie() {
        return Movie.builder()
                .name("Avengers")
                .producer("Br Russo")
                .releaseDate(LocalDate.of(2012, 10, 11))
                .genre(Genre.FANTASY)
                .country("USA")
                .build();
    }

    public static Review getNewReview() {
        return Review.builder()
                .grade(8)
                .text("Stupendous")
                .build();
    }

    public static User getNewUser(){
        return User.builder()
                .name("Joe")
                .surname("biden")
                .email("com34")
                .password("fhhfhhg")
                .role(Role.ADMIN)
                .build();
    }

}
