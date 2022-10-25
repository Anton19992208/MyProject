package springTest.ITTests;

import com.example.dao.ActorRepository;
import com.example.dao.MovieActorRepository;
import com.example.dao.MovieRepository;
import com.example.entity.Actor;
import com.example.entity.Movie;
import com.example.entity.MovieActor;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import springTest.configuration.ApplicationContext;
import util.TestUtil;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieTestIT {

   private static final MovieRepository movieRepository = ApplicationContext.getMovieRepository();
   private static final ActorRepository actorRepository = ApplicationContext.getActorRepository();
   private static final MovieActorRepository movieActorRepository = ApplicationContext.getMovieActorRepository();
   private static final Session session = (Session) ApplicationContext.getEntityManager();


    @Test
    void shouldCreateMovie() {
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        actorRepository.save(actor);
        movieRepository.save(movie);
        movieActor.setMovie(movie);
        movieActor.setActor(actor);

        movieRepository.save(movie);

        assertThat(movie.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void ShouldDeleteMovie(){
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        movieRepository.save(movie);
        session.flush();
        session.clear();

        movieRepository.delete(movie);

        assertThat(session.find(Movie.class, movie.getId())).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateMovie(){
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Actor actor = TestUtil.getActor();
        Actor newActor = TestUtil.getNewActor();
        MovieActor movieActor = TestUtil.getMovieActor();
        actorRepository.save(newActor);
        actorRepository.save(actor);
        movieRepository.save(movie);
        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        movieActorRepository.save(movieActor);

        movie.setName("PulpFiction2");
        movie.setCountry("UN");
        movieActor.setActor(newActor);
        movieRepository.update(movie);
        session.flush();
        session.clear();

        var updatedMovie = session.get(Movie.class, movie.getId());
        assertThat(movie.getId()).isEqualTo(updatedMovie.getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindMovieById(){
        session.beginTransaction();
        Movie actualMovie = TestUtil.getMovie();
        movieRepository.save(actualMovie);
        session.flush();
        session.clear();

        Optional<Movie> expectedMovie = movieRepository.findById(actualMovie.getId());

        assertThat(actualMovie.getId()).isEqualTo(expectedMovie.get().getId());
        session.getTransaction().rollback();
    }

    @Test
    void shouldFindAllMovies(){
        session.beginTransaction();
        Movie movie = TestUtil.getMovie();
        Movie newMovie = TestUtil.getNewMovie();
        movieRepository.save(movie);
        movieRepository.save(newMovie);
        session.flush();
        session.clear();

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(2);

        List<String> movieNames = movies.stream().map(Movie::name).collect(toList());
        assertThat(movieNames).containsExactlyInAnyOrder("Pulp Fiction", "Avengers");
        session.getTransaction().rollback();
    }



}
