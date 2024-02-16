package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.foodapplication.home.view.HomeActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DURATION = 5000;
    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the main activity
                        sharedPreferences = getSharedPreferences(SplashActivity.PREFERENCES, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                            Intent i = new Intent(SplashActivity.this, MainActivity2.class);
                            startActivity(i);
                            finish();

                        }
                       else{
                           Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();}
                    }
                }, SPLASH_DURATION);

                 btnStart= findViewById(R.id.btnStartNow);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = getSharedPreferences(SplashActivity.PREFERENCES, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                            Intent i = new Intent(SplashActivity.this, MainActivity2.class);
                            startActivity(i);
                            finish();

                        }
                        else{
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();}
                    }
                    }
                );
            }
        }