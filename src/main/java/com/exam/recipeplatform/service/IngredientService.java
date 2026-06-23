package com.exam.recipeplatform.service;

import com.exam.recipeplatform.model.entity.Ingredient;
import java.util.List;
import java.util.UUID;

public interface IngredientService {
    Ingredient addIngredientToRecipe(UUID recipeId, Ingredient ingredient);
    List<Ingredient> getIngredientsByRecipe(UUID recipeId);
}