package com.example.CalPal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.CalPal.dto.NutritionInfoDTO;
import org.json.*;

@Service
public class NutritionixService {

    @Value("${nutritionix.app.id}")
    private String appId;

    @Value("${nutritionix.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NutritionixService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // For natural language queries like "1 egg"
    public NutritionInfoDTO getNutritionInfo(String query) {
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("query", query);

        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject json = new JSONObject(response.getBody());
            JSONArray foods = json.getJSONArray("foods");

            if (!foods.isEmpty()) {
                JSONObject food = foods.getJSONObject(0);
                return new NutritionInfoDTO(
                        food.optDouble("nf_calories", 0),
                        food.optDouble("nf_total_fat", 0),
                        food.optDouble("nf_total_carbohydrate", 0),
                        food.optDouble("nf_protein", 0));
            }
        }
        return new NutritionInfoDTO(0, 0, 0, 0);
    }

    // ✅ For UPC code scanning
    public NutritionInfoDTO getFoodByUPC(String upc) {
        String url = "https://trackapi.nutritionix.com/v2/search/item?upc=" + upc;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject json = new JSONObject(response.getBody());
            JSONObject food = json.getJSONObject("foods");

            return new NutritionInfoDTO(
                    food.optDouble("nf_calories", 0),
                    food.optDouble("nf_total_fat", 0),
                    food.optDouble("nf_total_carbohydrate", 0),
                    food.optDouble("nf_protein", 0));
        }
        return new NutritionInfoDTO(0, 0, 0, 0);
    }
}
