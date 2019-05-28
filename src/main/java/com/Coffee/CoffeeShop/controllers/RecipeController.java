package com.Coffee.CoffeeShop.controllers;

import com.Coffee.CoffeeShop.models.Recipe;
import com.Coffee.CoffeeShop.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        return "recipes/showAll";
    }

    @GetMapping("/recipes/add")
    public String showSignUpForm(Recipe recipe) {
        return "recipes/add";
    }

    @PostMapping("/recipes")
    public String addRecipe(@Valid Recipe recipe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "recipes/add";
        }

        recipeRepository.save(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/{id}")
    public String getRecipe(@PathVariable("id") long id, Model model) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipes id: " + id));

        model.addAttribute("recipe", recipe);
        return "recipes/update";
    }

    @PostMapping("/recipes/{id}")
    public String updateRecipe(@PathVariable("id") long id, @Valid Recipe recipe,
                             BindingResult result) {
        if (result.hasErrors()) {
            recipe.setRecipe_id(id);
            return "redirect:/recipes/" + id;
        }

        recipe.setRecipe_id(id);
        recipeRepository.save(recipe);
        return "redirect:/recipes";
    }

    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable("id") long id, Model model) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid recipes Id:" + id));

        recipeRepository.delete(recipe);
        model.addAttribute("recipes", recipeRepository.findAll());
        return "recipes/showAll";
    }
}
