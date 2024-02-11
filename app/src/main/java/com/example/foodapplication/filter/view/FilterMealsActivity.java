package com.example.foodapplication.filter.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.filter.controller.FilterPresenter;
import com.example.foodapplication.network.RemoteDBSource;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class FilterMealsActivity extends AppCompatActivity implements IFilterMealsView, OnFilterClickListener {
    RecyclerView recyclerView,recyclerView2;
    GridLayoutManager layoutManager,layoutManager2;
    FilterTypeAdapter typeAdapter;
    MealsAdapter mealsAdapter;
    static int type;
    boolean isShow=false;
    FilterPresenter mealsPresenter;
 ChipGroup chipGroup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_meals);

        chipGroup=findViewById(R.id.chipGroup);
        setUpFilterChips();

        recyclerView = findViewById(R.id.recyclerViewFilter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,4,RecyclerView.VERTICAL,false);
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
                    mealsPresenter=new FilterPresenter(FilterMealsActivity.this, Repository.getRepository(RemoteDBSource.getInstance()));

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
        typeAdapter.setValues(meals);
        typeAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onFilterClickListener(String name) {

        mealsPresenter=new FilterPresenter(FilterMealsActivity.this, Repository.getRepository(RemoteDBSource.getInstance()),name);
        if(type==0){
            mealsPresenter.filterMealsByCategories();
        }
        else if(type==1)
        {
            mealsPresenter.filterMealsByIngredient();
        }
        else
        mealsPresenter.filterMealsByArea();

        recyclerView2 = findViewById(R.id.recyclerViewFilterMeals);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter =new MealsAdapter(this);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(mealsAdapter);

    }
}