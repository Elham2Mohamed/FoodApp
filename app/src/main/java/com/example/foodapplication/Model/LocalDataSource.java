package com.example.foodapplication.Model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.db.MealDAO;
import com.example.foodapplication.db.AppDataBase;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class LocalDataSource implements ILocalDataSource{
    private MealDAO mealDAO;
    private static LocalDataSource LocalDataSource =null;
    private Flowable<List<Meal>> Meals;
    public LocalDataSource(Context context) {
        AppDataBase dataBase =AppDataBase.getInstance(context.getApplicationContext());
        mealDAO =dataBase.getMealsDAO();

        Meals = mealDAO.getAllMeals();

    }
    public static LocalDataSource getInstance(Context context){
        if(LocalDataSource ==null)
            LocalDataSource =new LocalDataSource(context);
        return LocalDataSource;
    }
    @Override
    public void insertMeal(Meal product) {
        new Thread(){
            public void run() {
                mealDAO.insertMeal(product);
            }
        }.start();
    }


    @Override
    public void deleteMeal(Meal product) {
        new Thread(){
            public void run() {
                mealDAO.deleteMeal(product);
            }
        }.start();
    }

    @Override
    public Flowable<List<Meal>> getAllStoreMeals() {
        return Meals;
    }

}
