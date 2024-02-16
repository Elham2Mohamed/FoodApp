package com.example.foodapplication.searchFragment.view;

import com.example.foodapplication.Model.Meal;

public interface OnSearchClickListener {
    void onFavMealClickListener(Meal meal);
    void onMealDetailsClickListener(String name);
}
