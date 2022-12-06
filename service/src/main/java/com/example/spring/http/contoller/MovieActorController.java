package com.example.spring.http.contoller;

import com.example.spring.dto.MovieActorCreateEditDto;
import com.example.spring.service.MovieActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movieActors")
public class MovieActorController {

    private final MovieActorService movieActorService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("movieActor", movieActorService.findAll());
        return "movieActor/movieActors";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        return movieActorService.findById(id)
                .map(movieActor -> {
                    model.addAttribute("movieActor", movieActor);
                    return "movieActor/movieActor";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public String create(MovieActorCreateEditDto movie, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("movieActor", movie);
        return "redirect:/movieActors/" + movieActorService.create(movie).getId();
    }
}
