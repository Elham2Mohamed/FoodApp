package com.example.foodapplication.AllMeals.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import com.example.foodapplication.AllMeals.controller.AllMealsPresenter;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.home.controller.HomePresenter;
import com.example.foodapplication.home.view.CategoriesAdapter;
import com.example.foodapplication.home.view.HomeActivity;
import com.example.foodapplication.network.RemoteDBSource;

import java.util.List;

public class AllMealsActivity extends AppCompatActivity implements IAllMealsView {
    RecyclerView recyclerView;
     TextView type;
     ImageButton btnBack;
    GridLayoutManager layoutManager;
    MealsAdapter mealsAdapter;

    AllMealsPresenter mealsPresenter;
    String categoryName="Side";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_meals);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("name")) {

            categoryName = intent.getStringExtra("name");
        }

        btnBack=findViewById(R.id.btnBack);
        type=findViewById(R.id.textType);
        type.setText(categoryName);
        recyclerView = findViewById(R.id.recyclerViewAllMeals);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter =new MealsAdapter(this);
        mealsPresenter=new AllMealsPresenter( this, Repository.getRepository(RemoteDBSource.getInstance()),categoryName);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealsAdapter);
        mealsPresenter.getMealsByCategories();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllMealsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<Meal> meals) {
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}