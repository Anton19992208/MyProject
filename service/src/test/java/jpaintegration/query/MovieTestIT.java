package jpaintegration.query;

import annotation.IT;
import com.example.spring.dto.MovieDto;
import com.example.spring.entity.Movie;
import com.example.spring.repository.MovieRepository;
import com.querydsl.core.Tuple;
import jpaintegration.util.DataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class MovieTestIT {

    private final EntityManager entityManager;
    private final MovieRepository movieRepository;

    @Test
    void findMovieByName() {
        DataImporter.importData(entityManager);
        var movie = movieRepository.findMovieByName("KingdomOfHeaven");

        assertThat(movie).isNotNull();
    }

    @Test
    void findLimitedMoviesOrderedByReleaseDate() {
        DataImporter.importData(entityManager);
        var page = PageRequest.of(0, 2, Sort.by("releaseDate"));
        var moviesOnEachPage = movieRepository.findLimitedMoviesOrderedByReleaseDate(page);
        assertThat(moviesOnEachPage).hasSize(1);
    }

    @Test
    void findLimitedMoviesOrderedByReviewGrade() {
        DataImporter.importData(entityManager);
        var page = PageRequest.of(0, 2, Sort.by("id"));
        var movieWithGoodGrades = movieRepository.findLimitedMoviesOrderedByReviewGrade(page, 8.6);
        assertThat(movieWithGoodGrades).hasSize(2);

        List<String> moviesNames = movieWithGoodGrades.stream().map(Movie::name).collect(toList());
        assertThat(moviesNames).containsExactlyInAnyOrder("KingdomOfHeaven", "SinCity");
    }

    @Test
    void findAllMoviesByActorName() {
        DataImporter.importData(entityManager);
        var result = movieRepository.findAllMovieByActorName("Nikita");
        assertThat(result).hasSize(1);

        List<String> movieNames = result.stream().map(Movie::name).collect(toList());
        assertThat(movieNames).containsExactlyInAnyOrder("KingdomOfHeaven");
    }

    @Test
    void findMovieWithAvgGradeOrderedByMovieName() {
        DataImporter.importData(entityManager);
        List<Object[]> results = movieRepository.findMovieWithAvgGradeOrderedByMovieName();
        assertThat(results).hasSize(3);

        List<String> movNames = results.stream().map(a -> (String) a[0]).collect(toList());
        assertThat(movNames).contains("KingdomOfHeaven", "SinCity", "RussianMovie");

        List<Double> avgGrades = results.stream().map(a -> (Double) a[1]).collect(toList());
        assertThat(avgGrades).contains(10.0, 9.0, 3.0);
    }
}
