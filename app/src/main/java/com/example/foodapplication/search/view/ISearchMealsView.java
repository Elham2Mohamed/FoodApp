package com.example.foodapplication.search.view;

import com.example.foodapplication.Model.Meal;

import java.util.List;

public interface ISearchMealsView {
    public void showMeals(List<Meal> meals);
    public  void ShowErrMsg(String error);
}

