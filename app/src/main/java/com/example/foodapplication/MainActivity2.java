package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.network.RemoteDBSource;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.BroadcastReceiver;
import android.view.View;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.FlowableSubscriber;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView navigationView;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    static  int x=1,y=1;
    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
   public static SharedPreferences sharedPreferences;
   SharedPreferences.Editor editor;


    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkInternetConnection();
        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        checkInternetConnection();
        setLocale("en");


        sharedPreferences = getSharedPreferences(MainActivity2.PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("email") && intent.hasExtra("password")) {
            //saveData
            editor.putString(MainActivity2.EMAIL, intent.getStringExtra("email"));
            editor.putString(MainActivity2.PASSWORD, intent.getStringExtra("password"));
            editor.commit();
        }


        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();





        navigationView=findViewById(R.id.bottom_navigation);

        NavController navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        if (!sharedPreferences.contains("email") || !sharedPreferences.contains("password")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.favouriteFragment).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem item) {

                    showCreateAccountDialog();
                    menu.findItem(R.id.favouriteFragment).setEnabled(false);
                    return false;
                }
            });

            menu.findItem(R.id.weeklyFragment).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem item) {

                    showCreateAccountDialog();
                    menu.findItem(R.id.weeklyFragment).setEnabled(false);
                    return false;
                }
            });
        }



    }
    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkChangeReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkChangeReceiver();
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void unregisterNetworkChangeReceiver() {
        unregisterReceiver(networkChangeReceiver);
    }

    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            showNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your internet connection and try again.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }


    private  void showCreateAccountDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Required");
        builder.setMessage("You must create an account before accessing this feature.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            Intent intent=new Intent(MainActivity2.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("email", sharedPreferences.getString(EMAIL, null));
            bundle.putString("password", sharedPreferences.getString(PASSWORD, null));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    private  void showLogoutConfirmationDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {

            editor.clear();
            editor.apply();
            gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {


                    startActivity(new Intent(MainActivity2.this, LoginActivity.class));
                    finish();  }
            });
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
    private void setLocale(String languageCode) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}


