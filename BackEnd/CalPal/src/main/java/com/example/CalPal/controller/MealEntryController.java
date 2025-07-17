package com.example.CalPal.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.CalPal.entity.MealEntry;
import com.example.CalPal.repository.MealEntryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MealEntryController {

    private final MealEntryRepository mealRepo;

    @GetMapping("/{userId}/{date}")
    public List<MealEntry> getMealsByUserAndDate(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return mealRepo.findByUserIdAndDate(userId, date);
    }

    @PostMapping
    public MealEntry addMeal(@RequestBody MealEntry mealEntry) {
        return mealRepo.save(mealEntry);
    }
}
