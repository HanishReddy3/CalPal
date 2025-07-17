package com.example.CalPal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private String name;
    private Long userId;
    private List<IngredientDTO> ingredients;
}
