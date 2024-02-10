package com.example.foodapplication.network;

import com.example.foodapplication.Model.CategoriesResponse;
import com.example.foodapplication.Model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    // Search meal by name
    @GET("search.php")
    Call<MealResponse> searchMealByName(@Query("s") String mealName);

    // Lookup full meal details by id
    @GET("lookup.php")
    Call<MealResponse> lookupMealById(@Query("i") String mealId);

    // Lookup a single random meal
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    // List all meal categories
    @GET("categories.php")
    Call<CategoriesResponse> getAllCategories();

    // Filter by main ingredient
    @GET("filter.php")
    Call<MealResponse> filterByIngredient(@Query("i") String ingredient);

    // Filter by Category
    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);

    // Filter by Area
    @GET("filter.php")
    Call<MealResponse> filterByArea(@Query("a") String area);

    @GET("list.php?c=list")
    Call<MealResponse> getCategoriesName();
    @GET("list.php?a=list")
    Call<MealResponse> getAreaName();
    @GET("list.php?i=list")
    Call<MealResponse> getIngredientName();

}
