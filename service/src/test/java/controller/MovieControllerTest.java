package controller;

import com.example.spring.dto.MovieReadDto;
import jpaintegration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.example.spring.dto.MovieReadDto.Fields.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class MovieControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("movie/movies"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", hasSize(3)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/movies")
                        .param(name, "test.com")
                        .param(producer, "test1")
                        .param(releaseDate, "1999-01-02")
                        .param(country, "USA")
                        .param(genre, "COMEDY")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/movies/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {

    }
}