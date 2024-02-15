package com.example.foodapplication.Meal.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapplication.Meal.Controller.MealPresenter;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.MealIngredients;
import com.example.foodapplication.Model.MealUtils;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.network.RemoteDBSource;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealActivity extends AppCompatActivity implements IMealView{
    private TextView mealNameTextView,mealDescTextView;
    private RecyclerView ingredientsRecyclerView;
    private MealIngredientsAdapter adapter;
    private MealPresenter presenter;
    private WebView video;
    private ImageView image;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

                mealNameTextView = findViewById(R.id.mealNameTextView);
                mealDescTextView=findViewById(R.id.txtDesciption);
                video=findViewById(R.id.videoView);
                image=findViewById(R.id.pagerProductImage);
                ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
                 adapter = new MealIngredientsAdapter();
                ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                ingredientsRecyclerView.setAdapter(adapter);

                //String mealName = getIntent().getStringExtra("mealName");
                 String mealName ="Arrabiata";
                Repository repository =(Repository.getRepository(LocalDataSource.getInstance(this), RemoteDBSource.getInstance()));
                presenter = new MealPresenter(this, repository, mealName);
                presenter.getMealDetails();
            }
    @Override
    public void showMeal(Meal meal) {
        mealNameTextView.setText(meal.getStrMeal());
        mealDescTextView.setText(meal.getStrInstructions());
        // Load video URL in WebView
        video.loadUrl(meal.getStrYoutube());
        Picasso.get().load(meal.getStrMealThumb()).into(image);
        List<MealIngredients> ingredientsList = MealUtils.extractIngredients(meal);
        adapter.setIngredientsList(ingredientsList);
    }

            @Override
            public void ShowErrMsg(String error) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }
}