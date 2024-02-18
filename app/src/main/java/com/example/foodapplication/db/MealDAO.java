package com.example.foodapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodapplication.Model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDAO {
    @Query("SELECT* FROM meals")
    Flowable<List<Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal product);
    @Query("DELETE FROM meals")
    void deleteAllMeals();
    @Delete
    void deleteMeal(Meal product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Meal> meals);
}
