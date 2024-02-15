package com.example.foodapplication.Favorite.controller;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.foodapplication.Favorite.view.FavMealView;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import java.util.List;

public class FavMealPresenter implements IFavMealPresenter {
    private FavMealView view;
    private Repository repository;

    public FavMealPresenter(FavMealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getFavMeal() {
        LiveData<List<Meal>> meals= repository.getMeals();
        if (meals != null) {
                    view.showData((LiveData<List<Meal>>) meals);
                }
          else {
            Log.i("TAG", "getFavMeal: ");
        }
    }

    @Override
    public void deleteFromFav(Meal meal) {
  repository.removeMeal(meal);
    }



}
