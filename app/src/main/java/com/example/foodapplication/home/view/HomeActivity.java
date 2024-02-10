package com.example.foodapplication.home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.AllMeals.View.AllMealsActivity;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.R;
import com.example.foodapplication.home.controller.HomePresenter;
import com.example.foodapplication.network.RemoteDBSource;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IAllCategoriestView, IRandomMealsView,OnCategoriesClickListener {

    RecyclerView recyclerView;
    ImageView imageView;
    TextView name,mealCategories;
    GridLayoutManager layoutManager;
    CategoriesAdapter categoriesAdapter;

    HomePresenter allpresenter;
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
        allpresenter=new HomePresenter( this,this, Repository.getRepository(RemoteDBSource.getInstance()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoriesAdapter);
       allpresenter.getCategories();
        allpresenter.getRandomMeal();

        // BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
//
//                int id = item.getItemId();
//                switch (id)
//                {
//                case R.id.navigation_home:
//                    // Handle Home item click
//                    // Example: navigate to home fragment or activity
//                    return true;
//                case R.id.navigation_search:
//                    Intent intent = new Intent(HomeActivity.this, SearchActivity2.class);
//                    startActivity(intent);
//                    return true;
//                case R.id.navigation_weekly:
//                    // Handle Weekly item click
//                    // Example: navigate to weekly fragment or activity
//                    return true;
//                case R.id.navigation_favourite:
//                    // Handle Favourite item click
//                    // Example: navigate to favourite fragment or activity
//                    return true;
//                default:
//                    return false;
//            }
//        }
//        });
    }



private void init(){
    recyclerView = findViewById(R.id.recyclerViewCat);
    imageView=findViewById(R.id.imgRaFood);
    name=findViewById(R.id.txtRaFoodName);
    mealCategories=findViewById(R.id.txtRaFoodCat);
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
}