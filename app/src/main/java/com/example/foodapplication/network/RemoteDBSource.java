package com.example.foodapplication.network;


import android.util.Log;

import com.example.foodapplication.Model.CategoriesResponse;
import com.example.foodapplication.Model.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDBSource implements IRemoteDataSource {
    private static final String TAG = "ProductClient";
    private static final String BASE_URL="https://www.themealdb.com/api/json/v1/1/";
    private static RemoteDBSource remoteDBSource =null;
    private Service service;
    private RemoteDBSource() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(Service.class);


    }
    public static RemoteDBSource getInstance(){
        if(remoteDBSource ==null)
            remoteDBSource =new RemoteDBSource();
        return remoteDBSource;
    }
    public void makeNetworkCallCategories(NetworkCallback networkCallback){
       Call<CategoriesResponse> call= service.getAllCategories();
       call.enqueue(new Callback<CategoriesResponse>() {
           @Override
           public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
               if(response.isSuccessful()) {
                   assert response.body() != null;
                   networkCallback.onSuccessResultCategories(response.body().getCategories());
                   Log.i(TAG, "onResponse:onSuccessResult ");

               }
               else {
                   networkCallback.onFailureResultCategories("Response not successful");
               }
           }

           @Override
           public void onFailure(Call<CategoriesResponse> call, Throwable t) {
             networkCallback.onFailureResultCategories(t.getMessage());
             t.printStackTrace();
               Log.i(TAG, "onResponse:onFailureResult ");
           }
       });
    }

    @Override
    public void makeNetworkCallRandomMeal(NetworkCallback networkCallback) {
        Call<MealResponse> call= service.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    networkCallback.onSuccessResultMeals(response.body().getMeals());
                    Log.i(TAG, "onResponse:onSuccessResult ");

                }
                else {
                    networkCallback.onFailureResultMeals("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onFailureResultMeals(t.getMessage());
                t.printStackTrace();
                Log.i(TAG, "onResponse:onFailureResult ");
            }
        });
    }

    @Override
    public void makeNetworkCallSearchByCategory(NetworkCallback networkCallback, String categoryName) {
        Call<MealResponse> call= service.filterByCategory(categoryName);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    networkCallback.onSuccessResultMeals(response.body().getMeals());
                    Log.i(TAG, "onResponse:onSuccessResult ");

                }
                else {
                    networkCallback.onFailureResultMeals("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onFailureResultMeals(t.getMessage());
                t.printStackTrace();
                Log.i(TAG, "onResponse:onFailureResult ");
            }
        });
    }
    public void makeNetworkCallSearchByIngredient(NetworkCallback networkCallback,String ingredient){
        Call<MealResponse> call= service.filterByIngredient(ingredient);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    networkCallback.onSuccessResultMeals(response.body().getMeals());
                    Log.i(TAG, "onResponse:onSuccessResult ");

                }
                else {
                    networkCallback.onFailureResultMeals("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onFailureResultMeals(t.getMessage());
                t.printStackTrace();
                Log.i(TAG, "onResponse:onFailureResult ");
            }
        });
    }

    @Override
    public void makeNetworkCallSearchByName(NetworkCallback networkCallback, String name) {
        Call<MealResponse> call= service.searchMealByName(name);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    networkCallback.onSuccessResultMeals(response.body().getMeals());
                    Log.i(TAG, "onResponse:onSuccessResult ");

                }
                else {
                    networkCallback.onFailureResultMeals("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onFailureResultMeals(t.getMessage());
                t.printStackTrace();
                Log.i(TAG, "onResponse:onFailureResult ");
            }
        });
    }



    public void makeNetworkCallSearchByArea(NetworkCallback networkCallback,String area){
        Call<MealResponse> call= service.filterByArea(area);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    networkCallback.onSuccessResultMeals(response.body().getMeals());
                    Log.i(TAG, "onResponse:onSuccessResult ");

                }
                else {
                    networkCallback.onFailureResultMeals("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onFailureResultMeals(t.getMessage());
                t.printStackTrace();
                Log.i(TAG, "onResponse:onFailureResult ");
            }
        });
    }
}
