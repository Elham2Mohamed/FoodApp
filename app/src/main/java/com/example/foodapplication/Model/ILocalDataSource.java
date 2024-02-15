package com.example.foodapplication.Model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ILocalDataSource {
    void insertMeal(Meal product);
    void deleteMeal(Meal product);
    LiveData<List<Meal>> getAllStoreMeals();


}
