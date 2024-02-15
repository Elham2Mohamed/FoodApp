package com.example.foodapplication.Model;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.network.NetworkCallback;

import java.util.List;

public class Repository {
   LocalDataSource localDataSource;
    RemoteDBSource remoteDBSource;
    private static Repository repository=null;
    private Repository(LocalDataSource localDataSource, RemoteDBSource remoteDBSource) {
        this.localDataSource = localDataSource;
        this.remoteDBSource = remoteDBSource;
    }

    // Factory method to create Repository instance with both LocalDataSource and RemoteDBSource
    public static Repository getRepository(LocalDataSource localDataSource, RemoteDBSource remoteDBSource) {
        if (repository == null)
            repository = new Repository(localDataSource, remoteDBSource);
        return repository;
    }

    public void getMealDetailsByName(NetworkCallback networkCallback,String name){
        remoteDBSource.makeNetworkCallSearchByName(networkCallback,name);
    }
    public LiveData<List<Meal>> getMeals() {
        return localDataSource.getAllStoreMeals();
    }


    public void addMeal(Meal meal){
        localDataSource.insertMeal(meal);
    }

    public void removeMeal(Meal meal){
        localDataSource.deleteMeal(meal);
    }


    public void getAllCat(NetworkCallback networkCallback){
        remoteDBSource.makeNetworkCallCategories(networkCallback);
    }
    public void getAllArea(NetworkCallback networkCallback){
        remoteDBSource.makeNetworkCallArea(networkCallback);
    }
    public void getAllCATName(NetworkCallback networkCallback){
        remoteDBSource.makeNetworkCallCategorie(networkCallback);
    }
    public void getAllIng(NetworkCallback networkCallback){
        remoteDBSource.makeNetworkCallIngredients(networkCallback);
    }
    public void getRandomMeal(NetworkCallback networkCallback){
        remoteDBSource.makeNetworkCallRandomMeal(networkCallback);
    }
    public void getMealsByCategories(NetworkCallback networkCallback,String categoryName){
        remoteDBSource.makeNetworkCallSearchByCategory(networkCallback,categoryName);
    }
    public void getMealsByName(NetworkCallback networkCallback,String name){
        remoteDBSource.makeNetworkCallSearchByName(networkCallback,name);
    }

    public void getMealsByIngredient(NetworkCallback networkCallback,String ingredient){
        remoteDBSource.makeNetworkCallSearchByIngredient(networkCallback,ingredient);
    }
    public void getMealsByArea(NetworkCallback networkCallback,String area){
        remoteDBSource.makeNetworkCallSearchByArea(networkCallback,area);
    }
}
