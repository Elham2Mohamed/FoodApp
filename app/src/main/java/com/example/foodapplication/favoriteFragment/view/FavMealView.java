package com.example.foodapplication.favoriteFragment.view;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.Model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavMealView {
    public void showData(Flowable<List<Meal>> products);

}
