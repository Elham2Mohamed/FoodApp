package com.example.foodapplication.Meal.Controller;

import com.example.foodapplication.Model.Meal;

public interface IMealPresenter {
    void getMealDetails();
    public void addToFav(Meal meal);
}
