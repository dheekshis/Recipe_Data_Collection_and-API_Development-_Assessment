package com.example.recipeapi.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "recipes")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String cuisine;
    private Float rating;
    private Integer prep_time;
    private Integer cook_time;
    private Integer total_time;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Embedded
    private Nutrients nutrients;
    private String serves;

}

