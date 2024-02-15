package com.example.foodapplication.Model;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MealUtils {
    public static List<MealIngredients> extractIngredients(Meal meal) {
        List<MealIngredients> ingredientsList = new ArrayList<>();
        try {
            for (int i = 1; i <= 20; i++) {
                String ingredientFieldName = "strIngredient" + i;
                String measureFieldName = "strMeasure" + i;
                Field ingredientField = meal.getClass().getDeclaredField(ingredientFieldName);
                Field measureField = meal.getClass().getDeclaredField(measureFieldName);
                ingredientField.setAccessible(true);
                measureField.setAccessible(true);
                String ingredient = (String) ingredientField.get(meal);
                String measure = (String) measureField.get(meal);
                if (ingredient != null && !ingredient.isEmpty() && measure != null && !measure.isEmpty()) {
                    MealIngredients mealIngredients = new MealIngredients(ingredient, measure);
                    ingredientsList.add(mealIngredients);
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ingredientsList;
    }
}