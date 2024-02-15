package com.example.foodapplication.search.controller;

import com.example.foodapplication.Model.Meal;

public interface ISearchMealsPresenter {
    public void getMealsByCategories();
    public void getMealsByArea();
    public void getMealsByName();
    public void getMealsByIngredient();
    public void addToFav(Meal meal);
}
