package com.example.foodapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodapplication.Model.Meal;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
   private static AppDataBase instance=null;
   public abstract MealDAO getMealsDAO();
   public static synchronized AppDataBase getInstance(Context context){
       if(instance==null){
           instance= Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"Products").build();
       }
       return instance;
   }
}
