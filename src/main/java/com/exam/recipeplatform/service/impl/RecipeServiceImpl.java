package com.exam.recipeplatform.service.impl;

import com.exam.recipeplatform.exception.ResourceNotFoundException;
import com.exam.recipeplatform.exception.UnauthorizedException;
import com.exam.recipeplatform.model.entity.Recipe;
import com.exam.recipeplatform.model.entity.User;
import com.exam.recipeplatform.repository.RecipeRepository;
import com.exam.recipeplatform.repository.UserRepository;
import com.exam.recipeplatform.service.RecipeService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        recipe.setCreator(user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(UUID recipeId, Recipe updatedData, UUID userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        // Business Constraint: Only the owner can edit
        if (!recipe.getCreator().getId().equals(userId)) {
            throw new UnauthorizedException("You cannot edit someone else's recipe!");
        }

        recipe.setTitle(updatedData.getTitle());
        recipe.setInstructions(updatedData.getInstructions());
        recipe.setPreparationTime(updatedData.getPreparationTime());
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(UUID recipeId, UUID userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        // Business Constraint: Only the owner can delete
        if (!recipe.getCreator().getId().equals(userId)) {
            throw new UnauthorizedException("You cannot delete someone else's recipe!");
        }
        recipeRepository.delete(recipe);
    }

    @Override
    public Recipe getRecipeById(UUID recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
    }

    @Override
    public List<Recipe> getAllPublicRecipes() {
        return recipeRepository.findAll();
    }
}