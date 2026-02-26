package com.example.recipeapi.util;
import com.example.recipeapi.model.Recipe;
import com.example.recipeapi.repository.RecipeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
@Component
public class JsonDataLoader implements CommandLineRunner {
    private final RecipeRepository recipeRepository;
    public JsonDataLoader(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass()
                .getResourceAsStream("/US_recipes_null.json");
        Map<String, Recipe> recipesMap = mapper.readValue(
                inputStream,
                new TypeReference<Map<String, Recipe>>() {}
        );
        List<Recipe> recipes = recipesMap.values().stream().toList();
        for (Recipe recipe : recipes) {
            if (recipe.getPrep_time() != null && recipe.getCook_time() != null) {
                recipe.setTotal_time(
                        recipe.getPrep_time() + recipe.getCook_time());
            }
            // Only save if recipe title doesn't already exist
            if (!recipeRepository.existsByTitle(recipe.getTitle())) {
                recipeRepository.save(recipe);
            }
        }
    }
}






