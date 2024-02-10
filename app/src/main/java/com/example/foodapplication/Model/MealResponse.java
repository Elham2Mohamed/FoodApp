package com.example.foodapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
    @SerializedName("meals")
    List<Meal> Meals;


    // Getter Methods

    public List<Meal> getMeals() {
        return Meals;
    }

    // Setter Methods

    public void setMeals(List<Meal> meals) {
        this.Meals = meals;
    }
}
