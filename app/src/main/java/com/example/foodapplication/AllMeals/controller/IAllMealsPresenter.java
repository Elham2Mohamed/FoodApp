package com.example.foodapplication.AllMeals.controller;

import com.example.foodapplication.Model.Meal;

public interface IAllMealsPresenter {
    public void getMealsByCategories();
    public void addToFav(Meal meal);
}
