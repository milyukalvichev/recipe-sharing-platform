package com.exam.recipeplatform.web;

import com.exam.recipeplatform.model.entity.Review;
import com.exam.recipeplatform.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/recipes")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{id}/review")
    public String handleAddReview(@PathVariable UUID id,
                                  @RequestParam int rating,
                                  @RequestParam String comment,
                                  HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);

        reviewService.addReview(id, review, userId);
        return "redirect:/recipes/" + id;
    }
}