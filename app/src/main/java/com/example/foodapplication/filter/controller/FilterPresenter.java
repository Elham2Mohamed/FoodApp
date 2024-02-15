package com.example.foodapplication.filter.controller;

import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.filter.view.IFilterMealsView;
import com.example.foodapplication.network.NetworkCallback;

import java.util.List;

public class FilterPresenter implements IFilterPresenter, NetworkCallback {

    private IFilterMealsView mealsView;
    private Repository repository;
    String name;

    public FilterPresenter(IFilterMealsView allMealsView, Repository repository) {
        this.mealsView = allMealsView;
        this.repository = repository;

    }
    public FilterPresenter(IFilterMealsView allMealsView, Repository repository,String name) {
        this.mealsView = allMealsView;
        this.repository = repository;
        this.name=name;
    }

    @Override
    public void getMealsByCategories() {

            repository.getAllCATName(this);

    }
    public void addToFav(Meal meal) {
        repository.addMeal(meal);
    }

    @Override
    public void getMealsByArea() {
        repository.getAllArea(this);

    }
    public void filterMealsByArea() {
        repository.getMealsByArea(this,name);

    }
    public void filterMealsByCategories() {
        repository.getMealsByCategories(this,name);

    }
    public void filterMealsByIngredient() {
        repository.getMealsByIngredient(this,name);

    }

    @Override
    public void getMealsByIngredient() {
        repository.getAllIng(this);

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
        mealsView.showMeal(mealList);
    }

    @Override
    public void onFailureResultMeals(String errorMsg) {
        mealsView.ShowErrMsg(errorMsg);
    }


}
