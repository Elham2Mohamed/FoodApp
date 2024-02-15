package com.example.foodapplication.Model;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MealUtils {
    public static List<MealIngredients> extractIngredients(Meal meal) {
        List<MealIngredients> ingredientsList = new ArrayList<>();
        Field[] fields = meal.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (fieldName.startsWith("strIngredient") && field.get(meal) != null) {
                    String ingredientName = (String) field.get(meal);
                    String measureField = "strMeasure" + fieldName.substring("strIngredient".length());
                    Field measureFieldRef = meal.getClass().getDeclaredField(measureField);
                    measureFieldRef.setAccessible(true);
                    String measurement = (String) measureFieldRef.get(meal);
                    MealIngredients ingredient = new MealIngredients(ingredientName, measurement);
                    ingredientsList.add(ingredient);
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ingredientsList;
    }
}
