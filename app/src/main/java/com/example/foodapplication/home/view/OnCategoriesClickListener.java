package com.example.foodapplication.home.view;

import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;

public interface OnCategoriesClickListener {
    void onCategoriesClickListener(String name);
    void onFavMealClickListener(Meal meal);
    void onMealDetailsClickListener(String name);
}
