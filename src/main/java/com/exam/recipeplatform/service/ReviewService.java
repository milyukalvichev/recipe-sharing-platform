package com.exam.recipeplatform.service;

import com.exam.recipeplatform.model.entity.Review;
import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review addReview(UUID recipeId, Review review, UUID userId);
    List<Review> getReviewsByRecipe(UUID recipeId);
}