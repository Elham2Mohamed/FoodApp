package com.example.foodapplication.favoriteFragment.view;

import com.example.foodapplication.Model.Meal;
public interface OnMealClickListener {
    void onDeleteFavClickListener(Meal meal);
    void onMealDetailsClickListener(String name);
}
