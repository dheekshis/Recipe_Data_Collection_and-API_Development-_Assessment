package com.example.recipeapi.controller;

import com.example.recipeapi.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        // nothing special for now; database is pre‑loaded by JsonDataLoader
    }

    @Test
    void getAllRecipesReturnsData() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").exists());
    }

    @Test
    void postRecipeCreatesAndReturns201() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setCuisine("Test Cuisine");
        recipe.setPrep_time(10);
        recipe.setCook_time(5);

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.total_time").value(15));
    }

    @Test
    void postRecipeDuplicateTitleReturnsConflict() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setTitle("Sweet Potato Pie");
        recipe.setCuisine("Southern");
        recipe.setPrep_time(1);
        recipe.setCook_time(1);

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(recipe)))
                .andExpect(status().isConflict());
    }
}
