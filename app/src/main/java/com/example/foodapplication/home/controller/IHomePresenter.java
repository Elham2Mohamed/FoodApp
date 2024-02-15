package com.example.foodapplication.home.controller;


import com.example.foodapplication.Model.Meal;

public interface IHomePresenter {
    public void getCategories();
    public void getRandomMeal();
    public void addToFav(Meal meal);

}
