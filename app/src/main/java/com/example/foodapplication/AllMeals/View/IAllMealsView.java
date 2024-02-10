package com.example.foodapplication.AllMeals.View;

import com.example.foodapplication.Model.Meal;

import java.util.List;

public interface IAllMealsView {
    public void showMeals(List<Meal> meals);
    public  void ShowErrMsg(String error);
}

