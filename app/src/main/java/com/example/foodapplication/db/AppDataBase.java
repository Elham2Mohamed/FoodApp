package com.example.foodapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodapplication.Model.Meal;
@Database(entities = {Meal.class, MealEntry.class}, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
   private static AppDataBase instance=null;
   public abstract MealEntryDao mealEntryDao();
    public abstract MealDAO getMealsDAO();
    public static synchronized AppDataBase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"FAVMeals_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
