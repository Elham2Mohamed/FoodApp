package com.example.foodapplication.Meal.Controller;

import com.example.foodapplication.Meal.View.IMealView;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.network.NetworkCallback;

import java.util.List;

public class MealPresenter implements IMealPresenter, NetworkCallback {

    private IMealView mealView;
    private Repository repository;
    private String mealName;

    public MealPresenter(IMealView mealView, Repository repository, String mealName) {
        this.mealView = mealView;
        this.repository = repository;
        this.mealName = mealName;
    }

    @Override
    public void getMealDetails() {
        repository.getMealDetailsByName(this, mealName);
    }
    public void addToFav(Meal meal) {
        repository.addMeal(meal);
    }

    @Override
    public void onFailureResultMeals(String errorMsg) {
        mealView.ShowErrMsg("Failed to fetch meal details: " + errorMsg);
    }


    @Override
    public void onSuccessResultMeals(List<Meal> mealList) {
        if (mealList != null && !mealList.isEmpty()) {
            Meal meal = mealList.get(0); // Assuming only one meal is returned
            mealView.showMeal(meal);
        } else {
            mealView.ShowErrMsg("Meal not found");
        }
    }
    @Override
    public void onSuccessFilterMeals(List<Meal> mealList) {

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
}
