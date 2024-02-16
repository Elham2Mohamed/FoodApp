package com.example.foodapplication.network;

import com.example.foodapplication.Model.CategoriesResponse;
import com.example.foodapplication.Model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    // Search meal by name
    @GET("search.php")
    Single<MealResponse> searchMealByName(@Query("s") String mealName);

    // Lookup full meal details by id
    @GET("lookup.php")
    Single<MealResponse> lookupMealById(@Query("i") String mealId);

    // Lookup a single random meal
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    // List all meal categories
    @GET("categories.php")
    Single<CategoriesResponse> getAllCategories();

    // Filter by main ingredient
    @GET("filter.php")
    Single<MealResponse> filterByIngredient(@Query("i") String ingredient);

    // Filter by Category
    @GET("filter.php")
    Single<MealResponse> filterByCategory(@Query("c") String category);

    // Filter by Area
    @GET("filter.php")
    Single<MealResponse> filterByArea(@Query("a") String area);

    @GET("list.php?c=list")
    Single<MealResponse> getCategoriesName();
    @GET("list.php?a=list")
    Single<MealResponse> getAreaName();
    @GET("list.php?i=list")
    Single<MealResponse> getIngredientName();

}
