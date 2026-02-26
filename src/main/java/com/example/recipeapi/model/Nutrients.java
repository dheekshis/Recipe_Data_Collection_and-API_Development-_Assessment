package com.example.recipeapi.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrients {
    private String calories;
    private String carbohydrateContent;
    private String proteinContent;
    private String fatContent;

}

