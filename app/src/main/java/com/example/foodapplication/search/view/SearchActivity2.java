package com.example.foodapplication.search.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.foodapplication.AllMeals.View.IAllMealsView;
import com.example.foodapplication.AllMeals.View.MealsAdapter;
import com.example.foodapplication.AllMeals.controller.AllMealsPresenter;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.search.controller.SearchMealsPresenter;

import java.util.List;

public class SearchActivity2 extends AppCompatActivity implements ISearchMealsView {
    RecyclerView recyclerView;
    TextView type;
    ImageButton btnBack;
    GridLayoutManager layoutManager;
    SearchMealsAdapter mealsAdapter;

    SearchMealsPresenter mealsPresenter;
    TextView textCategory,textArea,textName,textIngredient;
    ImageButton btnSearch;
    EditText searchText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        textCategory=findViewById(R.id.textCategory);
        textArea=findViewById(R.id.textCountry);
        textName=findViewById(R.id.textName);
        textIngredient=findViewById(R.id.textIngredient);
        type=findViewById(R.id.textSearchType);
        btnSearch=findViewById(R.id.btnSearch);
        searchText=findViewById(R.id.textSreach);


        textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText(textArea.getText());
                btnSearch.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        mealsPresenter=new SearchMealsPresenter(SearchActivity2.this,Repository.getRepository(LocalDataSource.getInstance(SearchActivity2.this),RemoteDBSource.getInstance()),searchText.getText().toString());

                        mealsPresenter.getMealsByCategories();
                    }
                });


            }
        });
        textArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText(textArea.getText());
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mealsPresenter=new SearchMealsPresenter(SearchActivity2.this,Repository.getRepository(LocalDataSource.getInstance(SearchActivity2.this),RemoteDBSource.getInstance()),searchText.getText().toString());

                        mealsPresenter.getMealsByArea();
                    }
                });


            }
        });

        textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText(textName.getText());
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mealsPresenter=new SearchMealsPresenter(SearchActivity2.this,Repository.getRepository(LocalDataSource.getInstance(SearchActivity2.this),RemoteDBSource.getInstance()),searchText.getText().toString());

                        mealsPresenter.getMealsByName();
                    }
                });


            }
        });

        textIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText(textIngredient.getText());
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mealsPresenter=new SearchMealsPresenter(SearchActivity2.this,Repository.getRepository(LocalDataSource.getInstance(SearchActivity2.this),RemoteDBSource.getInstance()),searchText.getText().toString());

                        mealsPresenter.getMealsByIngredient();
                    }
                });


            }
        });


        recyclerView = findViewById(R.id.recyclerViewSearchMeals);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter =new SearchMealsAdapter(this);
       recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealsAdapter);


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<Meal> meals) {
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(error).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}