package com.example.foodapplication.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.AllMeals.View.AllMealsActivity;
import com.example.foodapplication.favoriteFragment.view.FavouriteFragment;
import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.MainActivity;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.R;
import com.example.foodapplication.filterFragment.view.FilterFragment;
import com.example.foodapplication.home.controller.HomePresenter;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.searchFragment.view.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IAllCategoriestView, IRandomMealsView,OnCategoriesClickListener {

    RecyclerView recyclerView;
    ImageView imageView;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;
    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView name,mealCategories;
    GridLayoutManager layoutManager;
    CategoriesAdapter categoriesAdapter;
    ImageButton btnFAV;
    HomePresenter allpresenter;
    CardView randomMeal;

    @SuppressLint({"MissingSuperCall", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        categoriesAdapter =new CategoriesAdapter(this,this);
        allpresenter=new HomePresenter( this,this, Repository.getRepository(LocalDataSource.getInstance(this),RemoteDBSource.getInstance()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoriesAdapter);
       allpresenter.getCategories();
        allpresenter.getRandomMeal();
        sharedPreferences = getSharedPreferences(HomeActivity.PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("email") && intent.hasExtra("password")) {
            //saveData
            editor.putString(HomeActivity.EMAIL, intent.getStringExtra("email"));
            editor.putString(HomeActivity.PASSWORD, intent.getStringExtra("password"));
            editor.commit();
        }


        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);
        sharedPreferences = getSharedPreferences(HomeActivity.PREFERENCES, Context.MODE_PRIVATE);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home_activity) {
                    return true;
                } else if (id == R.id.search_activity) {
                    Intent searchIntent = new Intent(HomeActivity.this, SearchFragment.class);
                    startActivity(searchIntent);
                    return true;
                } else if (id == R.id.filter_activity) {
                    Intent filterIntent = new Intent(HomeActivity.this, FilterFragment.class);
                    startActivity(filterIntent);
                    return true;
                }
             else if (id == R.id.navigation_favourite) {

                            if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                                // User has email and password stored, show logout confirmation dialog
                                Intent favoriteIntent = new Intent(HomeActivity.this, FavouriteFragment.class);
                                startActivity(favoriteIntent);
                            } else {
                                // User does not have email and password stored, show dialog
                                showCreateAccountDialog();
                            }

                return true;
            }
                else if (id == R.id.navigation_weekly) {

                    if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                        Intent favoriteIntent = new Intent(HomeActivity.this, FavouriteFragment.class);
                        startActivity(favoriteIntent);
                    } else {
                        showCreateAccountDialog();
                    }

                    return true;
                }
                return false;
            }
        });


    }



private void init(){
    ActionBar actionBar=getSupportActionBar();
    assert actionBar != null;
    actionBar.setHomeAsUpIndicator(R.drawable.logout);
    actionBar.setDisplayShowHomeEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);


    recyclerView = findViewById(R.id.recyclerViewCat);
    imageView=findViewById(R.id.imgRaFood);
    name=findViewById(R.id.txtRaFoodName);
    mealCategories=findViewById(R.id.txtRaFoodCat);
    btnFAV=findViewById(R.id.imgRafav);
    randomMeal=findViewById(R.id.ranMeal);
}
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                     showLogoutConfirmationDialog();
                } else {
                    showCreateAccountDialog();
              }
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showData(List<Categories> categoriesList) {
        categoriesAdapter.setValues(categoriesList);
        categoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRandomMeal(List<Meal> meals) {
        name.setText(meals.get(0).getStrMeal());
        mealCategories.setText(meals.get(0).getStrCategory());
        Picasso.get().load(meals.get(0).getStrMealThumb()).into(imageView);
        btnFAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                    HomeActivity.this.onFavMealClickListener(meals.get(0));
                } else {
                   showCreateAccountDialog();
                }

            }
        });
       randomMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.this.onMealDetailsClickListener(meals.get(0).getStrMeal());
            }
        });


    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onCategoriesClickListener(String name) {
        Intent intent = new Intent(this, AllMealsActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFavMealClickListener(Meal meal) {
        allpresenter.addToFav(meal);
    }
    @Override
    public void onMealDetailsClickListener(String name) {
        Intent intent = new Intent(this, MealActivity.class);
        intent.putExtra("mealName", name);
        startActivity(intent);
    }


    private void showCreateAccountDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Required");
        builder.setMessage("You must create an account before accessing this feature.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
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
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                }
            });
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}