package com.example.foodapplication.Model;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.db.MealEntry;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ILocalDataSource {
    void insertMeal(Meal product);
    public void insertMealToCal(MealEntry product);
    void deleteMeal(Meal product);
    Flowable<List<Meal>> getAllStoreMeals();
    void deleteMealCal(MealEntry mealEntry);
    Flowable<List<MealEntry>> getAllStoreCalMeals();
    public void deleteAllCalMeals();
    public void deleteAllFavMeals();
}
