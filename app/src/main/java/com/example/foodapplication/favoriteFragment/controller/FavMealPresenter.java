package com.example.foodapplication.favoriteFragment.controller;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.favoriteFragment.view.FavMealView;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavMealPresenter implements IFavMealPresenter {
    private FavMealView view;
    private Repository repository;

    public FavMealPresenter(FavMealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getFavMeal() {
        Flowable<List<Meal>> meals= repository.getMeals();
        if (meals != null) {
                    view.showData((Flowable<List<Meal>>) meals);
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
