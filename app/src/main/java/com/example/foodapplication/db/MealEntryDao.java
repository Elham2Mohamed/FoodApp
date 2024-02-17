package com.example.foodapplication.db;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodapplication.Model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealEntryDao {

    @Query("SELECT* FROM meal_entries")
    Flowable<List<MealEntry>> getAllCalMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealEntry mealEntry);
    @Query("DELETE FROM meal_entries")
    void deleteAllMeals();
    @Update
    void update(MealEntry mealEntry);

    @Delete
    void delete(MealEntry mealEntry);

}
