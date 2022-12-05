package com.example.spring.http.contoller;

import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.dto.PageResponse;
import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.entity.Genre;
import com.example.spring.entity.Role;
import com.example.spring.filter.MovieFilter;
import com.example.spring.mapper.MovieCreateEditMapper;
import com.example.spring.mapper.MovieReadMapper;
import com.example.spring.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public String findAll(Model model,
                          MovieFilter movieFilter,
                          Pageable pageable,
                          String name) {
        Page<MovieReadDto> page = movieService.findAll(movieFilter, pageable);
        model.addAttribute("movies", PageResponse.of(page));
        model.addAttribute("filter", movieFilter);
        model.addAttribute("byGrade", movieService.findAll(name, pageable));
        return "movie/movies";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return movieService.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    model.addAttribute("genres", Genre.values());
                    return "movie/movie";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(MovieCreateEditDto movie, RedirectAttributes redirectAttributes) {
        if (true) {
            redirectAttributes.addAttribute("movie", movie);
            return "redirect:/movies/registration";
        }
        return "redirect:/movies/" + movieService.create(movie).getId();
    }

    @GetMapping("/registration")
    public String registration(Model model, MovieCreateEditDto movie) {
        model.addAttribute("movie", movie);
        model.addAttribute("genre", Genre.values());
        return "movie/registration";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute MovieCreateEditDto movie) {
        return movieService.update(id, movie)
                .map(it -> "redirect:/movies/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/{id}/delete")
    public String delete(Long id) {
        if (!movieService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/movies";
    }
}
