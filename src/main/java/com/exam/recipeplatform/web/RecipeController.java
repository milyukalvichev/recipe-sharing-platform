package com.exam.recipeplatform.web;

import com.exam.recipeplatform.model.dto.RecipeCreateBindingModel;
import com.exam.recipeplatform.model.entity.Recipe;
import com.exam.recipeplatform.service.RecipeService;
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

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // CREATE (View Form)
    @GetMapping("/create")
    public String createForm(Model model) {
        if (!model.containsAttribute("recipeModel")) {
            model.addAttribute("recipeModel", new RecipeCreateBindingModel());
        }
        return "recipe-form";
    }

    // CREATE (Post Handler with Server-Side Validation)
    @PostMapping("/create")
    public String handleCreate(@Valid @ModelAttribute("recipeModel") RecipeCreateBindingModel recipeModel,
                               BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "recipe-form"; // Redisplay form to show red validation labels
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeModel.getTitle());
        recipe.setInstructions(recipeModel.getInstructions());
        recipe.setPreparationTime(recipeModel.getPreparationTime());

        recipeService.createRecipe(recipe, userId);
        return "redirect:/recipes";
    }

    // READ (Feed Dashboard)
    @GetMapping
    public String feedPage(Model model) {
        model.addAttribute("allRecipes", recipeService.getAllPublicRecipes());
        return "feed";
    }

    // UPDATE (View Form)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable UUID id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeCreateBindingModel bindingModel = new RecipeCreateBindingModel();
        bindingModel.setTitle(recipe.getTitle());
        bindingModel.setInstructions(recipe.getInstructions());
        bindingModel.setPreparationTime(recipe.getPreparationTime());

        model.addAttribute("recipeModel", bindingModel);
        model.addAttribute("recipeId", id);
        return "recipe-form";
    }

    // UPDATE (Put Handler)
    @PostMapping("/edit/{id}")
    public String handleUpdate(@PathVariable UUID id, @Valid @ModelAttribute("recipeModel") RecipeCreateBindingModel recipeModel,
                               BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "recipe-form";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        Recipe updatedData = new Recipe();
        updatedData.setTitle(recipeModel.getTitle());
        updatedData.setInstructions(recipeModel.getInstructions());
        updatedData.setPreparationTime(recipeModel.getPreparationTime());

        recipeService.updateRecipe(id, updatedData, userId);
        return "redirect:/recipes";
    }

    // DELETE Handler
    @PostMapping("/delete/{id}")
    public String handleDelete(@PathVariable UUID id, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        recipeService.deleteRecipe(id, userId);
        return "redirect:/recipes";
    }

    @GetMapping("/cookbook")
    public String personalCookbook(Model model, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        // Тук филтрираме рецептите, където създателят съвпада с логнатия потребител
        model.addAttribute("myRecipes", recipeService.getAllPublicRecipes().stream()
                .filter(r -> r.getCreator().getId().equals(userId))
                .toList());
        return "cookbook";
    }
}