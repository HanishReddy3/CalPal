package com.example.CalPal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.CalPal.entity.Recipe;
import com.example.CalPal.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeRepository recipeRepo;

    @GetMapping
    public List<Recipe> getAll() {
        return recipeRepo.findAll();
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeRepo.save(recipe);
    }
}
