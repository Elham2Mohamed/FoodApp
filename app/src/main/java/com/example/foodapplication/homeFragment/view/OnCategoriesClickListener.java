package com.example.foodapplication.homeFragment.view;

import com.example.foodapplication.Model.Meal;

public interface OnCategoriesClickListener {
    void onCategoriesClickListener(String name);
    void onFavMealClickListener(Meal meal);
    void onMealDetailsClickListener(String name);
}
