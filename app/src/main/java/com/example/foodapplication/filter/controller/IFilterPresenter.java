package com.example.foodapplication.filter.controller;

import com.example.foodapplication.Model.Meal;

public interface IFilterPresenter {
    public void getMealsByCategories();
    public void getMealsByArea();
    public void getMealsByIngredient();
    public void filterMealsByArea();
    public void filterMealsByCategories();
    public void filterMealsByIngredient();
    public void addToFav(Meal meal);
}
