package com.example.foodapplication.Meal.Controller;

import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.db.MealEntry;

public interface IMealPresenter {
    void getMealDetails();
    public void addToFav(Meal meal);
    public void addToCalender(MealEntry meal);
}
