package com.example.CalPal.controller;

import com.example.CalPal.dto.NutritionInfoDTO;
import com.example.CalPal.service.NutritionixService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutritionix")
@RequiredArgsConstructor
public class NutritionixController {

    @Autowired
    private NutritionixService nutritionixService;

    @GetMapping("/lookup")
    public ResponseEntity<NutritionInfoDTO> getFood(@RequestParam String upc) {
        NutritionInfoDTO response = nutritionixService.getFoodByUPC(upc);
        return ResponseEntity.ok(response);
    }
}
