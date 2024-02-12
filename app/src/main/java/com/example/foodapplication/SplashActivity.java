package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DURATION = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the main activity
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_DURATION);
            }
        }