package com.example.foodapplication.homeFragment.controller;


import com.example.foodapplication.Model.Meal;

public interface IHomePresenter {
    public void getCategories();
    public void getRandomMeal();
    public void addToFav(Meal meal);

}
