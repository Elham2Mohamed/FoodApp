package com.example.foodapplication.searchFragment.controller;

import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.network.NetworkCallback;
import com.example.foodapplication.searchFragment.view.ISearchMealsView;

import java.util.List;

public class SearchMealsPresenter implements ISearchMealsPresenter, NetworkCallback {

    private ISearchMealsView mealsView;
    private Repository repository;
    String name;

    public SearchMealsPresenter(ISearchMealsView allMealsView, Repository repository, String name) {
        this.mealsView = allMealsView;
        this.repository = repository;
        this.name=name;
    }

    @Override
    public void getMealsByCategories() {

            repository.getMealsByCategories(this,name);

    }
    public void addToFav(Meal meal) {
        repository.addMeal(meal);
    }

    @Override
    public void getMealsByArea() {
        repository.getMealsByArea(this,name);

    }

    @Override
    public void getMealsByName() {
        repository.getMealsByName(this,name);

    }

    @Override
    public void getMealsByIngredient() {
        repository.getMealsByIngredient(this,name);

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
