package com.example.CalPal.service;

import com.example.CalPal.dto.NutritionInfoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NutritionixService {

    @Value("${nutritionix.app.id}")
    private String appId;

    @Value("${nutritionix.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public NutritionixService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Natural language query (e.g., "1 egg")
    public NutritionInfoDTO getNutritionInfo(String query) {
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("query", query);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode foods = root.path("foods");
                if (foods.isArray() && foods.size() > 0) {
                    JsonNode food = foods.get(0);
                    return new NutritionInfoDTO(
                            food.path("nf_calories").asDouble(0),
                            food.path("nf_total_fat").asDouble(0),
                            food.path("nf_total_carbohydrate").asDouble(0),
                            food.path("nf_protein").asDouble(0));
                }
            } catch (Exception e) {
                e.printStackTrace(); // Consider using logger
            }
        }
        return new NutritionInfoDTO(0, 0, 0, 0);
    }

    // UPC Code
    public NutritionInfoDTO getFoodByUPC(String upc) {
        String url = "https://trackapi.nutritionix.com/v2/search/item?upc=" + upc;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode foods = root.path("foods");
                if (foods.isArray() && foods.size() > 0) {
                    JsonNode food = foods.get(0); // Get first item in array
                    return new NutritionInfoDTO(
                            food.path("nf_calories").asDouble(0),
                            food.path("nf_total_fat").asDouble(0),
                            food.path("nf_total_carbohydrate").asDouble(0),
                            food.path("nf_protein").asDouble(0));
                }
            } catch (Exception e) {
                e.printStackTrace(); // Consider using logger
            }
        }

        return new NutritionInfoDTO(0, 0, 0, 0);
    }
}
