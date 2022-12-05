package com.example.spring.http.contoller;

import com.example.spring.dto.ActorCreateEditDto;
import com.example.spring.dto.MovieCreateEditDto;
import com.example.spring.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("actors", actorService.findAll());
        return "actor/actors";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return actorService.findById(id)
                .map(actor -> {
                    model.addAttribute("actor", actor);
                    return "actor/actor";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public String create(ActorCreateEditDto actor, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("actor", actor);
        return "redirect:/actors/" + actorService.create(actor).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute ActorCreateEditDto actor) {
        return actorService.update(id, actor)
                .map(it -> "redirect:/actors/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(Long id) {
        if (!actorService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/actors";
    }

}
