package com.exam.recipeplatform.repository;

import com.exam.recipeplatform.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    // Custom query method can go here later if needed (e.g., finding recipes by creator)
}