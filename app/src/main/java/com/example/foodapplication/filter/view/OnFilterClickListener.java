package com.example.foodapplication.filter.view;

import com.example.foodapplication.Model.Meal;

public interface OnFilterClickListener {
    void onFilterClickListener(String name);
    void onFavMealClickListener(Meal meal);
    void onMealDetailsClickListener(String name);
}
