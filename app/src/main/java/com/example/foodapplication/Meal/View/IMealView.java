package com.example.foodapplication.Meal.View;

import com.example.foodapplication.Model.Meal;

public interface IMealView {
    void showMeal(Meal meal);
    void ShowErrMsg(String error);
}
