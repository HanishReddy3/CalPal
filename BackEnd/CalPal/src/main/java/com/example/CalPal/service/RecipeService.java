package com.example.CalPal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CalPal.dto.RecipeDTO;
import com.example.CalPal.entity.FoodItem;
import com.example.CalPal.entity.Ingredient;
import com.example.CalPal.entity.Recipe;
import com.example.CalPal.entity.User;
import com.example.CalPal.repository.FoodItemRepository;
import com.example.CalPal.repository.RecipeRepository;
import com.example.CalPal.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepo;
    private final UserRepository userRepo;
    private final FoodItemRepository foodRepo;

    public Recipe save(RecipeDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Ingredient> ingredients = dto.getIngredients().stream().map(i -> {
            FoodItem foodItem = foodRepo.findById(i.getFoodItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Food item not found"));
            return Ingredient.builder()
                    .foodItem(foodItem)
                    .quantity(i.getQuantity())
                    .build();
        }).toList();

        Recipe recipe = Recipe.builder()
                .name(dto.getName())
                .user(user)
                .ingredients(ingredients)
                .build();

        return recipeRepo.save(recipe);
    }

    public List<Recipe> getAll() {
        return recipeRepo.findAll();
    }
}
