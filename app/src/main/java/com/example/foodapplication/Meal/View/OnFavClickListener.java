package com.example.foodapplication.Meal.View;

import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.db.MealEntry;

public interface OnFavClickListener {
    void onFavMealClickListener(Meal meal);
    public void onSaveMealClickListener(MealEntry meal);

}
