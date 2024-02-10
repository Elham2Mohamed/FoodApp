package com.example.foodapplication.network;

public interface IRemoteDataSource {
    public void makeNetworkCallCategories(NetworkCallback networkCallback);
    public void makeNetworkCallRandomMeal(NetworkCallback networkCallback);
    public void makeNetworkCallSearchByCategory(NetworkCallback networkCallback, String categoryName);
     public void makeNetworkCallSearchByArea(NetworkCallback networkCallback,String Category);
    public void makeNetworkCallSearchByIngredient(NetworkCallback networkCallback,String Category);
    public void makeNetworkCallSearchByName(NetworkCallback networkCallback,String name);

    }

