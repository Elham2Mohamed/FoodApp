package com.example.foodapplication.weeklyMeal.controller;

import com.example.foodapplication.db.MealEntry;

public interface ICalMealPresenter {
    public void getCalMeal();
    public void deleteFromCal(MealEntry meal);
}
