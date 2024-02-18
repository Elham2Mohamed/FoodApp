package com.example.foodapplication.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.network.NetworkCallback;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Flowable;

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
    public Flowable<List<Meal>> getMeals() {
        return localDataSource.getAllStoreMeals();
    }


    public void addMeal(Meal meal){
        localDataSource.insertMeal(meal);
        uploadFAVMealToFirestore( meal);
    }
    public void addMealToCal(MealEntry meal){
        localDataSource.insertMealToCal(meal);
        uploadCalMealToFirestore(meal);
    }

    public void addAllMealToCal(List<MealEntry> meals){
        localDataSource.insertAllMealToCal(meals);
    }
    public void addAllMealToFAV(List<Meal> meals){
        localDataSource.insertAllMealToFAV(meals);
    }

    public void removeMeal(Meal meal){
        localDataSource.deleteMeal(meal);
    }
    public void removeMealFromCal(MealEntry meal){
        localDataSource.deleteMealCal(meal);
    }
    public Flowable<List<MealEntry>> getCalMeals() {
        return localDataSource.getAllStoreCalMeals();
    }

    public void deleteAllFavMeals() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> localDataSource.deleteAllFavMeals());
        executor.shutdown();
    }

    public void deleteAllCalMeals() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> localDataSource.deleteAllCalMeals());
        executor.shutdown();
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

    private void uploadFAVMealToFirestore(Meal meal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference favMealsRef = db.collection("FAVMeals");

        Map<String, Object> mealMap = new HashMap<>();
        mealMap.put("idMeal", meal.getIdMeal());
        mealMap.put("strMealThumb", meal.getStrMealThumb());
        mealMap.put("strMeal", meal.getStrMeal());

        favMealsRef.add(mealMap)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Meal added with ID: " + documentReference.getId());
                    })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding meal", e);
                     });
    }

    private void uploadCalMealToFirestore(MealEntry meal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference calMealsRef = db.collection("CALMeals");

        Map<String, Object> mealEntryMap = new HashMap<>();
        mealEntryMap.put("id", meal.getId());
        mealEntryMap.put("image", meal.getImage());
        mealEntryMap.put("name", meal.getName());
        mealEntryMap.put("date", meal.getDate());
        mealEntryMap.put("time", meal.getTime());
        // Add other fields as needed

        calMealsRef.add(mealEntryMap)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Meal entry added with ID: " + documentReference.getId());

                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding meal entry", e);
                });
    }
}
