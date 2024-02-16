package com.example.foodapplication.homeFragment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.AllMeals.View.AllMealsActivity;
import com.example.foodapplication.filterFragment.view.FilterFragment;
import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.filterFragment.view.FilterFragment;
import com.example.foodapplication.home.view.HomeActivity;
import com.example.foodapplication.homeFragment.controller.HomePresenter;
import com.example.foodapplication.homeFragment.view.CategoriesAdapter;
import com.example.foodapplication.homeFragment.view.IAllCategoriestView;
import com.example.foodapplication.homeFragment.view.IRandomMealsView;
import com.example.foodapplication.homeFragment.view.OnCategoriesClickListener;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.searchFragment.view.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.List;

public class HomeFragment extends Fragment implements IAllCategoriestView, IRandomMealsView, OnCategoriesClickListener {

    RecyclerView recyclerView;
    ImageView imageView;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;
    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    TextView name,mealCategories;
    GridLayoutManager layoutManager;
    CategoriesAdapter categoriesAdapter;
    ImageButton btnFAV;
    HomePresenter allpresenter;
    CardView randomMeal;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View view;
    String email,password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            email = bundle.getString("email");
            password = bundle.getString("password");
    }}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       this.view=view;

        init();

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        categoriesAdapter =new CategoriesAdapter(this,this);
        allpresenter=new HomePresenter( this, this, Repository.getRepository(LocalDataSource.getInstance(getContext()), RemoteDBSource.getInstance()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoriesAdapter);
        allpresenter.getCategories();
        allpresenter.getRandomMeal();
    }



    private void init(){
        recyclerView =view.findViewById(R.id.recyclerViewCat);
        imageView=view.findViewById(R.id.imgRaFood);
        name=view.findViewById(R.id.txtRaFoodName);
        mealCategories=view.findViewById(R.id.txtRaFoodCat);
        btnFAV=view.findViewById(R.id.imgRafav);
        randomMeal=view.findViewById(R.id.ranMeal);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
                // User has email and password stored, show logout confirmation dialog
                showLogoutConfirmationDialog();
            } else {
                // User does not have email and password stored, show dialog
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
                if (email==null && password==null) {
                    showCreateAccountDialog();

                } else {

                    HomeFragment.this.onFavMealClickListener(meals.get(0));
                }


            }
        });
        randomMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.onMealDetailsClickListener(meals.get(0).getStrMeal());
            }
        });


    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please check your internet connection and try again.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    @Override
    public void onCategoriesClickListener(String name) {
        Intent intent = new Intent(getContext(), AllMealsActivity.class);
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
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra("mealName", name);
        startActivity(intent);
    }

    private void showCreateAccountDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Account Required");
        builder.setMessage("You must create an account before accessing this feature.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            // Redirect user to login activity
            startActivity(new Intent(getContext(), LoginActivity.class));
            //finish(); // Finish MainActivity so user cannot return to it without logging in
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    private void showLogoutConfirmationDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // Clear user data from SharedPreferences
            editor.clear();
            editor.apply();
            // Sign out from Google if needed
            gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Redirect user to login activity
                    startActivity(new Intent(getContext(), LoginActivity.class));
                   // finish(); // Finish MainActivity so user cannot return to it without logging in
                }
            });
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}