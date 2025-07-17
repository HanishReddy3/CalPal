package com.example.CalPal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDTO {
    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    private String unit;
}