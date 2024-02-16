package com.example.foodapplication.network;


import android.util.Log;

import com.example.foodapplication.Model.CategoriesResponse;
import com.example.foodapplication.Model.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
        Retrofit retrofit =new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
          service = retrofit.create(Service.class);


    }
    public static RemoteDBSource getInstance(){
        if(remoteDBSource ==null)
            remoteDBSource =new RemoteDBSource();
        return remoteDBSource;
    }
    public void makeNetworkCallCategories(NetworkCallback networkCallback){
        Single<CategoriesResponse> call= service.getAllCategories();
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessResultCategories(item.getCategories());
                        },
                        error -> networkCallback.onFailureResultCategories(error.getMessage())

                );
           }

    @Override
    public void makeNetworkCallArea(NetworkCallback networkCallback) {
        Single<MealResponse> call= service.getAreaName();
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessFilterMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );

            }
    @Override
    public void makeNetworkCallCategorie(NetworkCallback networkCallback) {
        Single<MealResponse> call= service.getCategoriesName();
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessFilterMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }

    @Override
    public void makeNetworkCallIngredients(NetworkCallback networkCallback) {
        Single<MealResponse> call= service.getIngredientName();
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessFilterMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }

    @Override
    public void makeNetworkCallRandomMeal(NetworkCallback networkCallback) {
        Single<MealResponse> call= service.getRandomMeal();
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessResultMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }

    @Override
    public void makeNetworkCallSearchByCategory(NetworkCallback networkCallback, String categoryName) {
        Single<MealResponse> call= service.filterByCategory(categoryName);
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessResultMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }
    public void makeNetworkCallSearchByIngredient(NetworkCallback networkCallback,String ingredient){
        Single<MealResponse> call= service.filterByIngredient(ingredient);
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessResultMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }

    @Override
    public void makeNetworkCallSearchByName(NetworkCallback networkCallback, String name) {
        Single<MealResponse> call= service.searchMealByName(name);
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessResultMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }



    public void makeNetworkCallSearchByArea(NetworkCallback networkCallback,String area){
        Single<MealResponse> call= service.filterByArea(area);
        Disposable subscribe = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            assert item != null;
                            networkCallback.onSuccessResultMeals(item.getMeals());
                        },
                        error -> networkCallback.onFailureResultMeals(error.getMessage())

                );
    }
}
