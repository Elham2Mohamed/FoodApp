package com.example.foodapplication.weeklyMeal.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.foodapplication.R;

public class WeeklyMealActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_meal);
}}
// implements WeeklyMealView {
//    private RecyclerView recyclerView;
//    private WeeklyMealPresenter presenter;
//
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weekly_meal);
//
//        recyclerView = findViewById(R.id.recyclerViewWeekly);
//        presenter = new WeeklyMealPresenter(new Repository(AppDataBase.getInstance(this).getMealsDAO()), this);
//        presenter.getAllDailyMeals();
//    }
//
//    @Override
//    public void showMeals(List<Day> meals) {
//        // Update RecyclerView with meals
//    }
//    }
