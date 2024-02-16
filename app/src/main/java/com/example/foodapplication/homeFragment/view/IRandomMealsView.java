package com.example.foodapplication.homeFragment.view;

import com.example.foodapplication.Model.Meal;

import java.util.List;

public interface IRandomMealsView {
    public void showRandomMeal(List<Meal> meals);
    public  void ShowErrMsg(String error);
}

