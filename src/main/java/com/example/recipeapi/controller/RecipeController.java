package com.example.recipeapi.controller;
import com.example.recipeapi.model.Recipe;
import com.example.recipeapi.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
        if (recipe.getTitle() == null ||
                recipe.getCuisine() == null ||
                recipe.getPrep_time() == null ||
                recipe.getCook_time() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Required fields missing: title, cuisine, prep_time, cook_time");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if (recipeService.existsByTitle(recipe.getTitle())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "A recipe with that title already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }


    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/top")
    public Map<String, List<Recipe>> getTopRecipes(
            @RequestParam(defaultValue = "5") int limit) {
        List<Recipe> recipes = recipeService.getTopRecipes(limit);
        Map<String, List<Recipe>> response = new HashMap<>();
        response.put("data", recipes);
        return response;
    }
}
