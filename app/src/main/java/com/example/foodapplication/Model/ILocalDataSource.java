package com.example.foodapplication.Model;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ILocalDataSource {
    void insertMeal(Meal product);
    void deleteMeal(Meal product);
    Flowable<List<Meal>> getAllStoreMeals();


}
