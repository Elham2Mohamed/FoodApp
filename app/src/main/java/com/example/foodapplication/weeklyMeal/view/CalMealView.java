package com.example.foodapplication.weeklyMeal.view;

import com.example.foodapplication.db.MealEntry;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface CalMealView {
    public void showData(Flowable<List<MealEntry>> products);

}
