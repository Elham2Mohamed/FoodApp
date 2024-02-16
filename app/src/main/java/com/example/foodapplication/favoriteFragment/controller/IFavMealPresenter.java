package com.example.foodapplication.favoriteFragment.controller;

import com.example.foodapplication.Model.Meal;

public interface IFavMealPresenter {
    public void getFavMeal();
    public void deleteFromFav(Meal meal);
}
