package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    TextView userName;
    Button logout;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("email") && intent.hasExtra("password")) {
            //saveData
            editor.putString(MainActivity.EMAIL, intent.getStringExtra("email"));
            editor.putString(MainActivity.PASSWORD, intent.getStringExtra("password"));
            editor.commit();
        }
            logout = findViewById(R.id.logout);
            userName = findViewById(R.id.userName);

            gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gClient = GoogleSignIn.getClient(this, gOptions);
            sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);

            allowLogout();


            GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
            if (gAccount != null) {
                String gName = gAccount.getDisplayName();
                userName.setText(gName);
            }
        }

        private void allowLogout () {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                        // User has email and password stored, show logout confirmation dialog
                        showLogoutConfirmationDialog();
                    } else {
                        // User does not have email and password stored, show dialog
                        showCreateAccountDialog();
                    }
                }
            });
        }

        private void showCreateAccountDialog () {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Account Required");
            builder.setMessage("You must create an account before accessing this feature.");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                // Redirect user to login activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish(); // Finish MainActivity so user cannot return to it without logging in
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }

        private void showLogoutConfirmationDialog () {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout Confirmation");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                 editor.clear();
                editor.apply();

                gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            });
            builder.setNegativeButton("No", null);
            builder.show();
        }
    }
