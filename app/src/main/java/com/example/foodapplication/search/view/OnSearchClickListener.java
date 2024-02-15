package com.example.foodapplication.search.view;

import com.example.foodapplication.Model.Meal;

public interface OnSearchClickListener {
    void onFavMealClickListener(Meal meal);
    void onMealDetailsClickListener(String name);
}
