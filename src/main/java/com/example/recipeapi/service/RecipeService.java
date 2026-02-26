package com.example.recipeapi.service;
import com.example.recipeapi.model.Recipe;
import com.example.recipeapi.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public Recipe saveRecipe(Recipe recipe) {
        if (recipe.getPrep_time() == null || recipe.getCook_time() == null) {
            throw new RuntimeException("Prep time and Cook time required");
        }
        recipe.setTotal_time(recipe.getPrep_time() + recipe.getCook_time());
        return recipeRepository.save(recipe);
    }
    public List<Recipe> getTopRecipes(int limit) {
        return recipeRepository.findAll()
                .stream()
                .sorted((r1, r2) ->
                        Float.compare(
                                r2.getRating() == null ? 0 : r2.getRating(),
                                r1.getRating() == null ? 0 : r1.getRating()))
                .limit(limit)
                .toList();
    }

    /**
     * Return every recipe stored in the database.
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Check whether a recipe title is already stored.
     */
    public boolean existsByTitle(String title) {
        return recipeRepository.existsByTitle(title);
    }
}


