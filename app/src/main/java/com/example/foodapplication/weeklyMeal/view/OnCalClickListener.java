package com.example.foodapplication.weeklyMeal.view;

import com.example.foodapplication.db.MealEntry;

public interface OnCalClickListener {
    void onDeleteCalClickListener(MealEntry meal);
    void onMealDetailsClickListener(String name);
}
