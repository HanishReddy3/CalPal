package com.example.CalPal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.example.CalPal.entity.FoodItem;
import com.example.CalPal.repository.FoodItemRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemRepository foodItemRepo;

    @GetMapping
    public List<FoodItem> getAll() {
        return foodItemRepo.findAll();
    }

    @PostMapping
    public FoodItem add(@RequestBody FoodItem foodItem) {
        return foodItemRepo.save(foodItem);
    }
}