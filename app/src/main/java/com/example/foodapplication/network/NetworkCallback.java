package com.example.foodapplication.network;

import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;

import java.util.List;

public interface NetworkCallback {
    void getCategories();

    void getRandomMeal();

    public void onSuccessResultCategories(List<Categories> categories);
    public void onFailureResultCategories(String errorMsg);
    public void onSuccessResultMeals(List<Meal> mealList);
    public void onSuccessFilterMeals(List<Meal> mealList);
    public void onFailureResultMeals(String errorMsg);

}
