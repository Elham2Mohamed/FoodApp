package com.example.foodapplication.homeFragment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.foodapplication.MainActivity2;
import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.filterFragment.view.FilterFragment;
import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.filterFragment.view.FilterFragment;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.FlowableSubscriber;

public class HomeFragment extends Fragment implements IAllCategoriestView, IRandomMealsView, OnCategoriesClickListener {

    RecyclerView recyclerView;
    ImageView imageView;
    GoogleSignInClient gClient;
    Repository repository;
    GoogleSignInOptions gOptions;
    private static final String PREFERENCES = "PREFERENCES";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    TextView name,mealCategories;
    GridLayoutManager layoutManager;
    CategoriesAdapter categoriesAdapter;
    ImageButton btnFAV,btnLogout;
    HomePresenter allpresenter;
    CardView randomMeal;
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(getActivity(), gOptions);

        repository= Repository.getRepository(LocalDataSource.getInstance(getContext()), RemoteDBSource.getInstance());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HomeFragment.PREFERENCES, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        init();

        btnLogout=view.findViewById(R.id.btnlogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity2.sharedPreferences.contains("email") || !MainActivity2.sharedPreferences.contains("password")) {

                    showCreateAccountDialog();
                } else {
                    showLogoutConfirmationDialog();

                }
            }
        });
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
                if (!MainActivity2.sharedPreferences.contains("email") || !MainActivity2.sharedPreferences.contains("password")) {

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
            startActivity(new Intent(getContext(), LoginActivity.class));

        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    private void showLogoutConfirmationDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
           editor.clear();
            editor.apply();

            gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    deleteAllFavMealsFromRoom();
                    deleteAllCalMealsFromRoom();
                    startActivity(new Intent(getContext(), LoginActivity.class));

                }
            });
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void  deleteAllFavMealsFromRoom(){repository.deleteAllFavMeals();}
    private void  deleteAllCalMealsFromRoom(){repository.deleteAllCalMeals();}



}