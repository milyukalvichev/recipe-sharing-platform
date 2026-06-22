package com.exam.recipeplatform.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RecipeCreateBindingModel {

    @NotBlank(message = "Title cannot be blank!")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters!")
    private String title;

    @NotBlank(message = "Instructions cannot be blank!")
    @Size(min = 20, message = "Instructions must be at least 20 characters long!")
    private String instructions;

    @Min(value = 1, message = "Preparation time must be at least 1 minute!")
    private int preparationTime;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public int getPreparationTime() { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }
}