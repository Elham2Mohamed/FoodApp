package com.example.foodapplication.filterFragment.view;

import com.example.foodapplication.Model.Meal;

import java.util.List;

public interface IFilterMealsView {
    public void showMeals(List<Meal> meals);
    public void showMeal(List<Meal> meals);
    public  void ShowErrMsg(String error);
}

