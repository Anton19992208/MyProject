package util;

import com.example.entity.*;

import java.time.LocalDate;

public class TestUtil {

    public static User getUser() {
        User user = User.builder()
                .name("ffk")
                .surname("Stark")
                .email("@robStark")
                .password("21232420")
                .build();

        return new User();
    }

    public static Movie getMovie(){
        Movie movie = Movie.builder()
                .name("Pulp Fiction")
                .producer("Quentin Tarantino")
                .releaseDate(new ReleaseDate(LocalDate.of(1994,10, 14)))
                .genre(Genre.COMEDY)
                .country("USA")
                .build();

        return new Movie();

    }

    public static Actor getActor(){
        Actor actor = Actor.builder()
                .name("John")
                .surname("Travolta")
                .birthdate(new Birthday(LocalDate.of(1966,12,12)))
                .movie(new Movie())
                .build();

        return new Actor();
    }

    public static Review getReview(){
        Review review = Review.builder()
                .grade(9)
                .text("Cool")
                .movie(new Movie())
                .user(new User())
                .build();

        return new Review();
    }


}
