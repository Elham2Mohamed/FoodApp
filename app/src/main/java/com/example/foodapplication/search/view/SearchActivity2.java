package com.example.foodapplication.search.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.search.controller.SearchMealsPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class SearchActivity2 extends AppCompatActivity implements ISearchMealsView,OnSearchClickListener {
    RecyclerView recyclerView;
    TextView type;
    ImageButton btnBack;
    GridLayoutManager layoutManager;
    SearchMealsAdapter mealsAdapter;

    SearchMealsPresenter mealsPresenter;
    TextView textCategory,textArea,textName,textIngredient;
    ImageButton btnSearch;
    List<Meal> mealsNames;
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
                type.setText(textCategory.getText());
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
                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        filterNames(charSequence.toString());
                        mealsPresenter=new SearchMealsPresenter(SearchActivity2.this,Repository.getRepository(LocalDataSource.getInstance(SearchActivity2.this),RemoteDBSource.getInstance()),searchText.getText().toString());
                        mealsPresenter.getMealsByName();

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {}
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
        mealsAdapter =new SearchMealsAdapter(this,this);
       recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealsAdapter);


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<Meal> meals) {
        mealsNames=meals;
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
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFavMealClickListener(Meal meal) {
        mealsPresenter.addToFav(meal);
    }
    @Override
    public void onMealDetailsClickListener(String name) {
        Intent intent = new Intent(this, MealActivity.class);
        intent.putExtra("mealName", name);
        startActivity(intent);
    }
    @SuppressLint({"NotifyDataSetChanged", "CheckResult"})
    private void filterNames(String query) {
        // Check if mealsNames is null
        if (mealsNames == null) {
            return;
        }

        Observable.create((ObservableOnSubscribe<String>) emitter -> {

                    List<Meal> filteredNames = mealsNames.stream()
                            .filter(meal -> meal.getStrMeal().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());


                    emitter.onNext(query);
                    emitter.onComplete();
                })
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateAdapter(String query) {
        List<Meal> filteredNames = mealsNames.stream()
                .filter(meal -> meal.getStrMeal().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        mealsAdapter.setMeals(filteredNames);
        mealsAdapter.notifyDataSetChanged();
    }
}