package com.example.foodapplication.home.controller;


import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.home.view.IAllCategoriestView;
import com.example.foodapplication.home.view.IRandomMealsView;
import com.example.foodapplication.network.NetworkCallback;

import java.util.List;

public class HomePresenter implements IHomePresenter, NetworkCallback {
    private IAllCategoriestView allCategoriestView;
    private IRandomMealsView randomMealView;
    private Repository repository;

    public HomePresenter(IRandomMealsView randomMealView, IAllCategoriestView allCategoriestView, Repository repository) {
        this.randomMealView = randomMealView;
        this.allCategoriestView = allCategoriestView;
        this.repository = repository;
    }

    @Override

    public void getCategories() {
        repository.getAllCat(this);
    }

    @Override
    public void getRandomMeal() {
        repository.getRandomMeal(this);
    }


    @Override
    public void onSuccessResultCategories(List<Categories> categoriesList) {
 allCategoriestView.showData(categoriesList);
    }

    @Override
    public void onFailureResultCategories(String errorMsg) {
 allCategoriestView.ShowErrMsg(errorMsg);
    }

    @Override
    public void onSuccessResultMeals(List<Meal> mealList) {
randomMealView.showRandomMeal(mealList);
    }

    @Override
    public void onSuccessFilterMeals(List<Meal> mealList) {

    }

    @Override
    public void onFailureResultMeals(String errorMsg) {
randomMealView.ShowErrMsg(errorMsg);
    }
}
