package com.example.foodapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import androidx.annotation.NonNull;
@Entity(tableName = "Day")
public class Day {
    @PrimaryKey
    @NonNull
    private String name;
    private List<Meal> meals;

    public Day(@NonNull String name, List<Meal> meals) {
        this.name = name;
        this.meals = meals;
    }

    @NonNull
    public String getName() {

        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
