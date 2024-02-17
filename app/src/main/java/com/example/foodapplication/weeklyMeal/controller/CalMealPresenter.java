package com.example.foodapplication.weeklyMeal.controller;

import android.util.Log;


import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.weeklyMeal.view.CalMealView;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class CalMealPresenter implements ICalMealPresenter {
    private CalMealView view;
    private Repository repository;

    public CalMealPresenter(CalMealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getCalMeal() {
        Flowable<List<MealEntry>> meals= repository.getCalMeals();
        if (meals != null) {
                    view.showData((Flowable<List<MealEntry>>) meals);
                }
          else {
            Log.i("TAG", "getFavMeal: ");
        }
    }

    @Override
    public void deleteFromCal(MealEntry meal) {
  repository.removeMealFromCal(meal);
    }



}
