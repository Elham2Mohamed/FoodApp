package com.example.foodapplication.Model;

import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.network.NetworkCallback;

public class Repository {

    RemoteDBSource remoteDBSource;
    private static Repository repository=null;
    private Repository(RemoteDBSource remoteDBSource) {

        this.remoteDBSource = remoteDBSource;
    }
    public static Repository getRepository(RemoteDBSource remoteDBSource){
        if(repository==null)
            repository=new Repository(remoteDBSource);
        return repository;
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
