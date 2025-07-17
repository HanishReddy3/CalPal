package com.example.CalPal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user; // who created the recipe

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}