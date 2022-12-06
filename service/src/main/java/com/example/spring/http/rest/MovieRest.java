package com.example.spring.http.rest;

import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.dto.MovieReadDto;
import com.example.spring.dto.PageResponse;
import com.example.spring.dto.UserReadDto;
import com.example.spring.entity.Genre;
import com.example.spring.filter.MovieFilter;
import com.example.spring.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieRest {

    private final MovieService movieService;

    @GetMapping
    public PageResponse<MovieReadDto> findAll(MovieFilter filter, Pageable pageable) {
        Page<MovieReadDto> page = movieService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public MovieReadDto findById(@PathVariable("id") Long id) {
        return movieService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/{id}/avatar")
    public byte[] findAvatar(@PathVariable("id") Long id){
        return movieService.findAvatar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieReadDto create(@RequestBody MovieCreateEditDto movie) {
        return movieService.create(movie);
    }

    @PutMapping("/{id}")
    public MovieReadDto update(@PathVariable("id") Long id, @RequestBody MovieCreateEditDto movie) {
        return movieService.update(id, movie)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        if (!movieService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
