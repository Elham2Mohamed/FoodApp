package com.example.foodapplication.Meal.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.MainActivity2;
import com.example.foodapplication.Meal.Controller.MealPresenter;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.MealIngredients;
import com.example.foodapplication.Model.MealUtils;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.homeFragment.view.HomeFragment;
import com.example.foodapplication.network.RemoteDBSource;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Intent;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
public class MealActivity extends AppCompatActivity implements IMealView, OnFavClickListener {
    private TextView mealNameTextView, mealDescTextView;
    private RecyclerView ingredientsRecyclerView;
    private MealIngredientsAdapter adapter;
    private MealPresenter presenter;
    private WebView video;
    private ImageView image;

    private ImageButton btnAddFav,btnAddToCalender;

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
         btnAddToCalender=findViewById(R.id.btnAddCalendar);

        String mealName = getIntent().getStringExtra("mealName");
        Repository repository = Repository.getRepository(LocalDataSource.getInstance(this), RemoteDBSource.getInstance());
        presenter = new MealPresenter(this, repository, mealName);
        presenter.getMealDetails();
    }

    public void addToCalendar(Meal meal) {
        // Create a Calendar instance with the current date and time as the default values
        Calendar calendar = Calendar.getInstance();

        // Create a DatePickerDialog to allow the user to select the date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, selectedYear, selectedMonth, selectedDay) -> {
            // Create a TimePickerDialog to allow the user to select the time
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
                // Update the selected date and time
                MealActivity.this.onSaveMealClickListener(new MealEntry(meal.getStrMealThumb(),meal.getStrMeal(),selectedYear+" / "+(1+selectedMonth)+" / "+selectedDay, selectedHour+" : "+selectedMinute));

                calendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);

                // Create the calendar intent
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, meal.getStrMeal()) // Set the meal name as the event title
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis())
                        .putExtra(CalendarContract.Events.ALL_DAY, false)
                        .putExtra(CalendarContract.Events.DESCRIPTION, meal.getStrInstructions()) // Add meal description if needed
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, meal.getStrArea()); // Add meal location if needed

                // Start the calendar intent
                startActivity(calendarIntent);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

            // Show the TimePickerDialog
            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Set the minimum date to the current date
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Add 7 days to the current date and set it as the maximum date
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
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
                if (!MainActivity2.sharedPreferences.contains("email") || !MainActivity2.sharedPreferences.contains("password")) {

                    showCreateAccountDialog();

                } else {

                    MealActivity.this.onFavMealClickListener(meal);
                }

            }
        });
        btnAddToCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity2.sharedPreferences.contains("email") || !MainActivity2.sharedPreferences.contains("password")) {

                    showCreateAccountDialog();

                } else {

                    addToCalendar( meal) ;
                }


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
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSaveMealClickListener(MealEntry meal) {
        presenter.addToCalender(meal);
    }

    private void showCreateAccountDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Required");
        builder.setMessage("You must create an account before accessing this feature.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            // Redirect user to login activity
            startActivity(new Intent(this, LoginActivity.class));
            //finish(); // Finish MainActivity so user cannot return to it without logging in
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }
}
