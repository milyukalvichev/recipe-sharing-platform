package com.exam.recipeplatform.web;

import com.exam.recipeplatform.model.dto.RecipeCreateBindingModel;
import com.exam.recipeplatform.model.entity.Ingredient;
import com.exam.recipeplatform.model.entity.Recipe;
import com.exam.recipeplatform.service.IngredientService;
import com.exam.recipeplatform.service.RecipeService;
import com.exam.recipeplatform.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final ReviewService reviewService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, ReviewService reviewService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.reviewService = reviewService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String feedPage(Model model) {
        model.addAttribute("allRecipes", recipeService.getAllPublicRecipes());
        return "feed";
    }

    @GetMapping("/cookbook")
    public String personalCookbook(Model model, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) return "redirect:/login";

        model.addAttribute("myRecipes", recipeService.getAllPublicRecipes().stream()
                .filter(r -> r.getCreator() != null && r.getCreator().getId().equals(userId))
                .toList());
        return "cookbook";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        if (!model.containsAttribute("recipeModel")) {
            model.addAttribute("recipeModel", new RecipeCreateBindingModel());
        }
        return "recipe-form";
    }

    @PostMapping("/create")
    public String handleCreate(@Valid @ModelAttribute("recipeModel") RecipeCreateBindingModel recipeModel,
                               BindingResult bindingResult,
                               @RequestParam(required = false) String rawIngredients,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "recipe-form";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        if (userId == null) return "redirect:/login";

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeModel.getTitle());
        recipe.setInstructions(recipeModel.getInstructions());
        recipe.setPreparationTime(recipeModel.getPreparationTime());

        Recipe savedRecipe = recipeService.createRecipe(recipe, userId);

        // Обработка и записване на съставките, ако са въведени
        if (rawIngredients != null && !rawIngredients.isEmpty()) {
            String[] ingredientsArray = rawIngredients.split(",");
            for (String ingName : ingredientsArray) {
                if (!ingName.trim().isBlank()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingName.trim());
                    ingredient.setQuantity(1); // Дефолтна стойност
                    ingredient.setUnit("unit");
                    ingredientService.addIngredientToRecipe(savedRecipe.getId(), ingredient);
                }
            }
        }

        return "redirect:/recipes";
    }

    @GetMapping("/{id}")
    public String recipeDetails(@PathVariable UUID id, Model model) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            model.addAttribute("recipe", recipe);
            model.addAttribute("reviews", reviewService.getReviewsByRecipe(id));
            model.addAttribute("ingredients", ingredientService.getIngredientsByRecipe(id));
            return "details";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not retrieve recipe details: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable UUID id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeCreateBindingModel bindingModel = new RecipeCreateBindingModel();
        bindingModel.setTitle(recipe.getTitle());
        bindingModel.setInstructions(recipe.getInstructions());
        bindingModel.setPreparationTime(recipe.getPreparationTime());

        java.util.List<Ingredient> currentIngredients = ingredientService.getIngredientsByRecipe(id);
        String ingredientsText = String.join(", ", currentIngredients.stream().map(Ingredient::getName).toList());

        model.addAttribute("recipeModel", bindingModel);
        model.addAttribute("recipeId", id);
        model.addAttribute("rawIngredients", ingredientsText); // Подаваме ги към формата
        return "recipe-form";
    }

    @PostMapping("/edit/{id}")
    public String handleUpdate(@PathVariable UUID id,
                               @Valid @ModelAttribute("recipeModel") RecipeCreateBindingModel recipeModel,
                               BindingResult bindingResult,
                               @RequestParam(required = false) String rawIngredients,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "recipe-form";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        Recipe updatedData = new Recipe();
        updatedData.setTitle(recipeModel.getTitle());
        updatedData.setInstructions(recipeModel.getInstructions());
        updatedData.setPreparationTime(recipeModel.getPreparationTime());

        recipeService.updateRecipe(id, updatedData, userId);

        if (rawIngredients != null) {
            String[] ingredientsArray = rawIngredients.split(",");
            for (String ingName : ingredientsArray) {
                if (!ingName.trim().isBlank()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingName.trim());
                    ingredient.setQuantity(1);
                    ingredient.setUnit("unit");
                    ingredientService.addIngredientToRecipe(id, ingredient);
                }
            }
        }

        return "redirect:/recipes";
    }

    @PostMapping("/delete/{id}")
    public String handleDelete(@PathVariable UUID id, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        recipeService.deleteRecipe(id, userId);
        return "redirect:/recipes";
    }
}