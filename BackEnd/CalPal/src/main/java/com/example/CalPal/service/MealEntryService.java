package com.example.CalPal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CalPal.dto.MealEntryDTO;
import com.example.CalPal.entity.FoodItem;
import com.example.CalPal.entity.MealEntry;
import com.example.CalPal.entity.Recipe;
import com.example.CalPal.entity.User;
import com.example.CalPal.repository.FoodItemRepository;
import com.example.CalPal.repository.MealEntryRepository;
import com.example.CalPal.repository.RecipeRepository;
import com.example.CalPal.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealEntryService {

    private final MealEntryRepository mealRepo;
    private final UserRepository userRepo;
    private final FoodItemRepository foodRepo;
    private final RecipeRepository recipeRepo;

    public MealEntry save(MealEntryDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<FoodItem> foodItems = foodRepo.findAllById(dto.getFoodItemIds());
        List<Recipe> recipes = recipeRepo.findAllById(dto.getRecipeIds());

        LocalDate date = LocalDate.parse(dto.getDate());

        MealEntry meal = MealEntry.builder()
                .user(user)
                .date(date)
                .foodItems(foodItems)
                .recipes(recipes)
                .build();

        return mealRepo.save(meal);
    }

    public List<MealEntry> getMealsForUserOnDate(Long userId, LocalDate date) {
        return mealRepo.findByUserIdAndDate(userId, date);
    }
}
