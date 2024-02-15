package com.example.foodapplication.Meal.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
import com.example.foodapplication.home.view.HomeActivity;
import com.example.foodapplication.network.RemoteDBSource;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealActivity extends AppCompatActivity implements IMealView, OnFavClickListener {
    private TextView mealNameTextView, mealDescTextView;
    private RecyclerView ingredientsRecyclerView;
    private MealIngredientsAdapter adapter;
    private MealPresenter presenter;
    private WebView video;
    private ImageView image;
    private ImageButton btnAddFav;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        mealNameTextView = findViewById(R.id.mealNameTextView);
        mealDescTextView = findViewById(R.id.txtDesciption);
        video = findViewById(R.id.videoView);
        image = findViewById(R.id.pagerProductImage);
        btnAddFav = findViewById(R.id.btnAddFavourite);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        adapter = new MealIngredientsAdapter();
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsRecyclerView.setAdapter(adapter);

        String mealName = getIntent().getStringExtra("mealName");
        Repository repository = Repository.getRepository(LocalDataSource.getInstance(this), RemoteDBSource.getInstance());
        presenter = new MealPresenter(this, repository, mealName);
        presenter.getMealDetails();
    }

    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);
        if (matcher.find()) {
            videoId = matcher.group();
        }
        return videoId;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void showMeal(Meal meal) {
        mealNameTextView.setText(meal.getStrMeal());
        mealDescTextView.setText(meal.getStrInstructions());

        loadVideo(meal.getStrYoutube());
        Picasso.get().load(meal.getStrMealThumb()).into(image);

        List<MealIngredients> ingredientsList = MealUtils.extractIngredients(meal);
        if (!ingredientsList.isEmpty()) {
            adapter.setIngredientsList(ingredientsList);
        } else {
            Toast.makeText(this, "No ingredients available", Toast.LENGTH_SHORT).show();
        }

        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealActivity.this.onFavMealClickListener(meal);
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadVideo(String youtubeUrl) {
        String videoId = extractVideoId(youtubeUrl);
        if (videoId != null) {
            String embeddedVideoUrl = "https://www.youtube.com/embed/" + videoId;
            String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"" + embeddedVideoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
            video.loadData(videoHtml, "text/html", "utf-8");
            WebSettings webSettings = video.getSettings();
            webSettings.setJavaScriptEnabled(true);
            video.setWebChromeClient(new WebChromeClient());
        }
    }

    @Override
    protected void onDestroy() {
        if (video != null) {
            video.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void ShowErrMsg(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFavMealClickListener(Meal meal) {
        presenter.addToFav(meal);
    }
}
