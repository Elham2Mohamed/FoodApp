package com.example.foodapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
    @SerializedName("meals")
    List<Meal> Meals;


    public List<Meal> getMeals() {
        return Meals;
    }

    public void setMeals(List<Meal> meals) {
        this.Meals = meals;
    }
}
