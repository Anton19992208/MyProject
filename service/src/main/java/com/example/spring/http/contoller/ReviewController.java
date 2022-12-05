package com.example.spring.http.contoller;

import com.example.spring.dto.ActorCreateEditDto;
import com.example.spring.dto.ReviewCreateEditDto;
import com.example.spring.service.ReviewService;
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
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return "review/reviews";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return reviewService.findById(id)
                .map(review -> {
                    model.addAttribute("review", review);
                    return "review/review";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(ReviewCreateEditDto review, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("review", review);
        return "redirect:/reviews/" + reviewService.create(review).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute ReviewCreateEditDto review) {
        return reviewService.update(id, review)
                .map(it -> "redirect:/reviews/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(Long id) {
        if (!reviewService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/reviews";
    }
}
