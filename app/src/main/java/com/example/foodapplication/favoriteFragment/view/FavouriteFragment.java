package com.example.foodapplication.favoriteFragment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieImageAsset;
import com.example.foodapplication.AllMeals.View.AllMealsActivity;
import com.example.foodapplication.favoriteFragment.controller.FavMealPresenter;
import com.example.foodapplication.favoriteFragment.view.FavMealView;
import com.example.foodapplication.favoriteFragment.view.FavouriteFragment;
import com.example.foodapplication.favoriteFragment.view.FavoriteAdapter;
import com.example.foodapplication.favoriteFragment.view.OnMealClickListener;
import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.filterFragment.view.FilterFragment;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.searchFragment.view.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements OnMealClickListener, FavMealView {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FavoriteAdapter favoriteAdapter;
    ImageView fullScreenImage;
    FavMealPresenter favMealPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewFavorite);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        favMealPresenter =new FavMealPresenter(this , Repository.getRepository(LocalDataSource.getInstance(getContext()), RemoteDBSource.getInstance()));
        fullScreenImage = view.findViewById(R.id.fullScreenImage);
        favoriteAdapter = new FavoriteAdapter(this, new ArrayList<>(),FavouriteFragment.this);
        recyclerView.setAdapter(favoriteAdapter);
        favMealPresenter.getFavMeal();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDeleteFavClickListener(Meal meal) {


        favMealPresenter.deleteFromFav(meal);
        favoriteAdapter.notifyDataSetChanged();
    }

    @SuppressLint({"NotifyDataSetChanged", "CheckResult"})
    @Override
    public void showData(Flowable<List<Meal>> meals) {

        if (meals != null) {
            if (fullScreenImage != null) {
                fullScreenImage.setVisibility(View.GONE);
            }
            recyclerView.setVisibility(View.VISIBLE);
            meals.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item -> {
                                favoriteAdapter.setValues(item);
                                favoriteAdapter.notifyDataSetChanged();
                            }
                    );


        } else {
            recyclerView.setVisibility(View.GONE);
            fullScreenImage.setVisibility(View.VISIBLE);
        }


    }
    public void onMealDetailsClickListener(String name) {
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra("mealName", name);
        startActivity(intent);
    }
}