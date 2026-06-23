package com.exam.recipeplatform.service.impl;

import com.exam.recipeplatform.exception.ResourceNotFoundException;
import com.exam.recipeplatform.model.entity.Ingredient;
import com.exam.recipeplatform.model.entity.Recipe;
import com.exam.recipeplatform.repository.IngredientRepository;
import com.exam.recipeplatform.repository.RecipeRepository;
import com.exam.recipeplatform.service.IngredientService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Ingredient addIngredientToRecipe(UUID recipeId, Ingredient ingredient) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
        ingredient.setRecipe(recipe);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> getIngredientsByRecipe(UUID recipeId) {
        return ingredientRepository.findAllByRecipeId(recipeId);
    }
}