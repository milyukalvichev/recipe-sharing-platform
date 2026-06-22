package com.exam.recipeplatform.service;

import com.exam.recipeplatform.model.entity.Recipe;
import java.util.List;
import java.util.UUID;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe, UUID userId);
    Recipe updateRecipe(UUID recipeId, Recipe updatedData, UUID userId);
    void deleteRecipe(UUID recipeId, UUID userId);
    Recipe getRecipeById(UUID recipeId);
    List<Recipe> getAllPublicRecipes();
}