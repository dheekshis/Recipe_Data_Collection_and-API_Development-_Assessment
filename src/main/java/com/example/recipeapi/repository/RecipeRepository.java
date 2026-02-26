package com.example.recipeapi.repository;
import com.example.recipeapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findTop5ByOrderByRatingDesc();
    boolean existsByTitle(String title);
}

