package com.example.foodapplication.Favorite.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.foodapplication.Favorite.controller.FavMealPresenter;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.network.RemoteDBSource;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements OnMealClickListener, FavMealView {
        LiveData<List<Meal>> meals;
        RecyclerView recyclerView;
        LinearLayoutManager layoutManager;
        FavoriteAdapter favoriteAdapter;
        FavMealPresenter favMealPresenter;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recyclerViewFavorite);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        favMealPresenter =new FavMealPresenter(this , Repository.getRepository(LocalDataSource.getInstance(this), RemoteDBSource.getInstance()));


        favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, new ArrayList<>(),FavoriteActivity.this);
        recyclerView.setAdapter(favoriteAdapter);
        favMealPresenter.getFavMeal();
        }

@SuppressLint("NotifyDataSetChanged")
@Override
public void onDeleteFavClickListener(Meal meal) {


        favMealPresenter.deleteFromFav(meal);
        favoriteAdapter.notifyDataSetChanged();
        }

@SuppressLint("NotifyDataSetChanged")
@Override
public void showData(LiveData<List<Meal>> meals) {
        meals.observe(this, new Observer<List<Meal>>() {
@Override
public void onChanged(List<Meal> meals1) {
        favoriteAdapter.setValues(meals1);
        favoriteAdapter.notifyDataSetChanged();
      }
   });


}
        public void onMealDetailsClickListener(String name) {
                Intent intent = new Intent(this, MealActivity.class);
                intent.putExtra("mealName", name);
                startActivity(intent);
        }
}