package com.example.foodapplication.network;

import com.example.foodapplication.Model.CategoriesResponse;
import com.example.foodapplication.Model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("search.php")
    Single<MealResponse> searchMealByName(@Query("s") String mealName);


    @GET("lookup.php")
    Single<MealResponse> lookupMealById(@Query("i") String mealId);


    @GET("random.php")
    Single<MealResponse> getRandomMeal();


    @GET("categories.php")
    Single<CategoriesResponse> getAllCategories();


    @GET("filter.php")
    Single<MealResponse> filterByIngredient(@Query("i") String ingredient);


    @GET("filter.php")
    Single<MealResponse> filterByCategory(@Query("c") String category);


    @GET("filter.php")
    Single<MealResponse> filterByArea(@Query("a") String area);

    @GET("list.php?c=list")
    Single<MealResponse> getCategoriesName();
    @GET("list.php?a=list")
    Single<MealResponse> getAreaName();
    @GET("list.php?i=list")
    Single<MealResponse> getIngredientName();

}
