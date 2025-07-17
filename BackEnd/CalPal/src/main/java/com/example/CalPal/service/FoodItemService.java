package com.example.CalPal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.CalPal.dto.FoodItemDTO;
import com.example.CalPal.entity.FoodItem;
import com.example.CalPal.repository.FoodItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodRepo;

    public FoodItem save(FoodItemDTO dto) {
        FoodItem food = FoodItem.builder()
                .name(dto.getName())
                .calories(dto.getCalories())
                .protein(dto.getProtein())
                .carbs(dto.getCarbs())
                .fat(dto.getFat())
                .unit(dto.getUnit())
                .build();
        return foodRepo.save(food);
    }

    public List<FoodItemDTO> getAll() {
        return foodRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private FoodItemDTO toDTO(FoodItem food) {
        return new FoodItemDTO(
                food.getName(),
                food.getCalories(),
                food.getProtein(),
                food.getCarbs(),
                food.getFat(),
                food.getUnit());
    }
}