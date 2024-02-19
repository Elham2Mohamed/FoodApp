package com.example.foodapplication.filterFragment.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieImageAsset;
import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.MainActivity2;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.filterFragment.controller.FilterPresenter;
import com.example.foodapplication.filterFragment.view.FilterFragment;
import com.example.foodapplication.filterFragment.view.FilterTypeAdapter;
import com.example.foodapplication.filterFragment.view.MealsAdapter;
import com.example.foodapplication.homeFragment.view.HomeFragment;
import com.example.foodapplication.network.RemoteDBSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class FilterFragment extends Fragment implements IFilterMealsView, OnFilterClickListener {
    RecyclerView recyclerView,recyclerView2;
    GridLayoutManager layoutManager,layoutManager2;
    FilterTypeAdapter typeAdapter;
    MealsAdapter mealsAdapter;
    static int type;
    View view;
    boolean isShow=false;
    ImageView fullScreenImage;
    FilterPresenter mealsPresenter;
    ChipGroup chipGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         this.view=view;
        chipGroup=view.findViewById(R.id.chipGroup);
        setUpFilterChips();
        fullScreenImage = view.findViewById(R.id.fullScreenImage);
        recyclerView2 =view.findViewById(R.id.recyclerViewFilterMeals);
        recyclerView =view.findViewById(R.id.recyclerViewFilter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),4,RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        typeAdapter =new FilterTypeAdapter(this,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(typeAdapter);



    }

    private void setUpFilterChips() {
        for (int i=0;i<chipGroup.getChildCount();i++){
            Chip chip= (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mealsPresenter=new FilterPresenter(FilterFragment.this, Repository.getRepository(LocalDataSource.getInstance(getContext()), RemoteDBSource.getInstance()));

                    if(isChecked){
                        if (chip.getText().toString().equalsIgnoreCase("Category")) {

                            mealsPresenter.getMealsByCategories();
                            type=0;
                        }
                        else if (chip.getText().toString().equalsIgnoreCase("Ingredient")) {
                            mealsPresenter.getMealsByIngredient();
                            type=1;
                        }
                        else{
                            mealsPresenter.getMealsByArea();
                            type=2;
                        }
                    }
                }
            });
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<Meal> meals) {
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void showMeal(List<Meal> meals) {
        if (meals == null) {
            // Hide the RecyclerView
            recyclerView.setVisibility(View.GONE);
        } else {
           recyclerView.setVisibility(View.VISIBLE);
            fullScreenImage.setVisibility(View.GONE);
        typeAdapter.setValues(meals);
        typeAdapter.notifyDataSetChanged();
    }
    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your internet connection and try again.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    @Override
    public void onFilterClickListener(String name) {

        mealsPresenter=new FilterPresenter(FilterFragment.this, Repository.getRepository(LocalDataSource.getInstance(getContext()),RemoteDBSource.getInstance()),name);
        if(type==0){
            mealsPresenter.filterMealsByCategories();
        }
        else if(type==1)
        {
            mealsPresenter.filterMealsByIngredient();
        }
        else
            mealsPresenter.filterMealsByArea();


        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter =new MealsAdapter(this,this);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(mealsAdapter);

    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFavMealClickListener(Meal meal) {
        if (!MainActivity2.sharedPreferences.contains("email") || !MainActivity2.sharedPreferences.contains("password")) {

            showCreateAccountDialog();

        } else {

            mealsPresenter.addToFav(meal);
        }

    }
    private void showCreateAccountDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Account Required");
        builder.setMessage("You must create an account before accessing this feature.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }
    @Override
    public void onMealDetailsClickListener(String name) {
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra("mealName", name);
        startActivity(intent);
    }

}