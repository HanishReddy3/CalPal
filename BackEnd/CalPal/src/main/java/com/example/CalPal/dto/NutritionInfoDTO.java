package com.example.CalPal.dto;

public class NutritionInfoDTO {
    private double calories;
    private double fat;
    private double carbs;
    private double protein;

    public NutritionInfoDTO() {
    }

    public NutritionInfoDTO(double calories, double fat, double carbs, double protein) {
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
