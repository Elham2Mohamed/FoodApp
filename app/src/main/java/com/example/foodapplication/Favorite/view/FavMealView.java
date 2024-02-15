package com.example.foodapplication.Favorite.view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;


import com.example.foodapplication.Model.Meal;

import java.util.List;

public interface FavMealView {
    public void showData(LiveData<List<Meal>> products);

}
