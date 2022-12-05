package jpaintegration.crud;

import annotation.IT;
import com.example.spring.entity.Actor;
import com.example.spring.entity.Movie;
import com.example.spring.entity.MovieActor;
import com.example.spring.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import utils.TestUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

@IT
@RequiredArgsConstructor
public class MovieRepositoryTestIT {

    private final EntityManager entityManager;
    private final MovieRepository movieRepository;

    @Test
    void findById(){
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        entityManager.persist(movie);
        entityManager.persist(actor);
        movieActor.setActor(actor);
        movieActor.setMovie(movie);

        entityManager.persist(movie);

        assertThat(movie).isNotNull();
        assertThat(actor).isNotNull();


    }

    @Test
    void shouldDeleteMovie(){
        Movie movie = TestUtil.getMovie();
        entityManager.persist(movie);
        entityManager.flush();
        entityManager.clear();

        Movie movieToDelete = entityManager.find(Movie.class, movie.getId());
        movieRepository.delete(movieToDelete);

        assertThat(entityManager.find(Movie.class, movie.getId())).isNull();

    }

    @Test
    void shouldUpdateMovie(){
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        Actor newActor = TestUtil.getNewActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        entityManager.persist(newActor);
        entityManager.persist(actor);
        entityManager.persist(movie);
        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        entityManager.persist(movieActor);

        movie.setName("PulpFiction2");
        movie.setCountry("UN");
        movieActor.setActor(newActor);
        movieRepository.saveAndFlush(movie);
        entityManager.flush();
        entityManager.clear();

        var updatedMovie = entityManager.find(Movie.class, movie.getId());
        var updatedActor = entityManager.find(Actor.class, actor.getId());
        assertThat(movie.getId()).isEqualTo(updatedMovie.getId());
        assertThat(actor.getId()).isEqualTo(updatedActor.getId());

    }

    @Test
    void shouldFindById(){
        Movie actualMovie = TestUtil.getMovie();
        entityManager.persist(actualMovie);
        entityManager.flush();
        entityManager.clear();

        Optional<Movie> expectedMovie = movieRepository.findById(actualMovie.getId());

        assertThat(expectedMovie.get().getId()).isEqualTo(actualMovie.getId());
    }

    @Test
    void shouldFindAllMovies(){
        Movie movie = TestUtil.getMovie();
        Movie newMovie = TestUtil.getNewMovie();
        entityManager.persist(movie);
        entityManager.persist(newMovie);
        entityManager.flush();
        entityManager.clear();

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(2);

        List<String> movieNames = movies.stream().map(Movie::name).collect(Collectors.toList());
        assertThat(movieNames).containsExactlyInAnyOrder("Pulp Fiction", "Avengers");

    }
}
