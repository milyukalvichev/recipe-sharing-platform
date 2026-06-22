package com.exam.recipeplatform.service.impl;

import com.exam.recipeplatform.exception.ResourceNotFoundException;
import com.exam.recipeplatform.model.entity.Recipe;
import com.exam.recipeplatform.model.entity.Review;
import com.exam.recipeplatform.model.entity.User;
import com.exam.recipeplatform.repository.RecipeRepository;
import com.exam.recipeplatform.repository.ReviewRepository;
import com.exam.recipeplatform.repository.UserRepository;
import com.exam.recipeplatform.service.ReviewService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Review addReview(UUID recipeId, Review review, UUID userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        review.setRecipe(recipe);
        review.setAuthor(user);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByRecipe(UUID recipeId) {
        return reviewRepository.findAllByRecipeId(recipeId);
    }
}