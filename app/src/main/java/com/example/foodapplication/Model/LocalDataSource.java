package com.example.foodapplication.Model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodapplication.db.MealDAO;
import com.example.foodapplication.db.AppDataBase;
import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.db.MealEntryDao;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class LocalDataSource implements ILocalDataSource{
    private final MealDAO mealDAO;
    private final MealEntryDao mealEntryDao;
    private static LocalDataSource LocalDataSource =null;
    private final Flowable<List<Meal>> Meals;
    private final Flowable<List<MealEntry>> mealEntrys;

    public LocalDataSource(Context context) {
        AppDataBase dataBase =AppDataBase.getInstance(context.getApplicationContext());
        mealDAO =dataBase.getMealsDAO();
        mealEntryDao = dataBase.mealEntryDao();
        Meals = mealDAO.getAllMeals();
        mealEntrys=mealEntryDao.getAllCalMeals();

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
    public void insertMealToCal(MealEntry product) {
        new Thread(){
            public void run() {
                mealEntryDao.insert(product);
            }
        }.start();
    }
    @Override
    public void deleteAllFavMeals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteAllMeals();
            }
        }).start();
    }
@Override
    public void deleteAllCalMeals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealEntryDao.deleteAllMeals();
            }
        }).start();
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
    public void deleteMealCal(MealEntry mealEntry) {
        new Thread(){
            public void run() {
                mealEntryDao.delete(mealEntry);
            }
        }.start();
    }
    public Flowable<List<MealEntry>> getAllStoreCalMeals() {
        return mealEntrys;
    }
    }

