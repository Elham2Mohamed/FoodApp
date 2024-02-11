package com.example.foodapplication.AllMeals.controller;

import com.example.foodapplication.AllMeals.View.IAllMealsView;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.network.NetworkCallback;

import java.util.List;

public class AllMealsPresenter implements IAllMealsPresenter, NetworkCallback {

    private IAllMealsView mealsView;
    private Repository repository;
    String categoryName;

    public AllMealsPresenter(IAllMealsView allMealsView, Repository repository, String categoryName) {
        this.mealsView = allMealsView;
        this.repository = repository;
        this.categoryName=categoryName;
    }

    @Override
    public void getMealsByCategories() {

            repository.getMealsByCategories(this,categoryName);

    }


    @Override
    public void getCategories() {

    }

    @Override
    public void getRandomMeal() {

    }

    @Override
    public void onSuccessResultCategories(List<Categories> categories) {

    }

    @Override
    public void onFailureResultCategories(String errorMsg) {

    }

    @Override
    public void onSuccessResultMeals(List<Meal> mealList) {
        mealsView.showMeals(mealList);
    }

    @Override
    public void onSuccessFilterMeals(List<Meal> mealList) {

    }

    @Override
    public void onFailureResultMeals(String errorMsg) {
        mealsView.ShowErrMsg(errorMsg);
    }
}
