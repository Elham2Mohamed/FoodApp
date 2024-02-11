package com.example.foodapplication.filter.controller;

public interface IFilterPresenter {
    public void getMealsByCategories();
    public void getMealsByArea();
    public void getMealsByIngredient();
    public void filterMealsByArea();
    public void filterMealsByCategories();
    public void filterMealsByIngredient();
}
