package com.example.CalPal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealEntryDTO {
    private Long userId;
    private String date; // format: "YYYY-MM-DD"
    private List<Long> foodItemIds;
    private List<Long> recipeIds;
}
