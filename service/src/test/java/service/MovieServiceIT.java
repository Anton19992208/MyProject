package service;

import annotation.IT;
import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.entity.Genre;
import com.example.spring.service.MovieService;
import jpaintegration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class MovieServiceIT extends IntegrationTestBase{

    private final MovieService movieService;
    private static final Long MOVIE_ID = 1L;

    @Test
    void findAll(){
        List<MovieReadDto> result = movieService.findAll(null);
        assertThat(result).hasSize(3);
    }

    @Test
    void findById(){
        Optional<MovieReadDto> maybeMovie = movieService.findById(MOVIE_ID);
        assertThat(maybeMovie.isPresent());
        maybeMovie.ifPresent(movie -> assertEquals("Tor", movie.getName()));
    }

//    @Test
//    void create(){
//        MovieCreateEditDto movieCreateEditDto = new MovieCreateEditDto(
//                "StarWars",
//                "Somebody",
//                LocalDate.of(2000,11,10),
//                "USA",
//                Genre.FANTASY
//        );
//        MovieReadDto actualResult = movieService.create(movieCreateEditDto);
//        assertEquals(movieCreateEditDto.getName(), actualResult.getName());
//        assertEquals(movieCreateEditDto.getProducer(), actualResult.getProducer());
//        assertEquals(movieCreateEditDto.getCountry(), actualResult.getCountry());
//        assertSame(movieCreateEditDto.getGenre(), actualResult.getGenre());
//        assertEquals(movieCreateEditDto.getReleaseDate(), actualResult.getReleaseDate());
//    }
//
//    @Test
//    void update(){
//        MovieCreateEditDto movieCreateEditDto = new MovieCreateEditDto(
//                "StarWars2",
//                "Somebody3",
//                LocalDate.of(2002,12,10),
//                "USA",
//                Genre.FANTASY
//        );

//        Optional<MovieReadDto> actualResult = movieService.update(MOVIE_ID, movieCreateEditDto);
//        assertTrue(actualResult.isPresent());
//
//        actualResult.ifPresent(movie -> {
//            assertEquals(movieCreateEditDto.getName(), movie.getName());
//            assertEquals(movieCreateEditDto.getProducer(), movie.getProducer());
//            assertEquals(movieCreateEditDto.getCountry(), movie.getCountry());
//            assertSame(movieCreateEditDto.getGenre(), movie.getGenre());
//            assertEquals(movieCreateEditDto.getReleaseDate(), movie.getReleaseDate());
//        });
//    }

    @Test
    void delete(){
        assertFalse(movieService.delete(123L));
        assertTrue(movieService.delete(MOVIE_ID));
    }

}
