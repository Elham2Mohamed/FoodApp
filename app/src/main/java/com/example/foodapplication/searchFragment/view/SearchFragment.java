package com.example.foodapplication.searchFragment.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieImageAsset;
import com.example.foodapplication.LoginActivity;
import com.example.foodapplication.MainActivity2;
import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.searchFragment.controller.SearchMealsPresenter;
import com.example.foodapplication.searchFragment.view.SearchFragment;
import com.example.foodapplication.searchFragment.view.SearchMealsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements ISearchMealsView,OnSearchClickListener {
    RecyclerView recyclerView;
    TextView type;
    ImageButton btnBack;
    GridLayoutManager layoutManager;
    SearchMealsAdapter mealsAdapter;
     ImageView fullScreenImage;
    SearchMealsPresenter mealsPresenter;
    TextView textCategory,textArea,textName,textIngredient;
    ImageButton btnSearch;
    List<Meal> mealsNames;
    EditText searchText;
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        textCategory=view.findViewById(R.id.textCategory);
        textArea=view.findViewById(R.id.textCountry);
        textName=view.findViewById(R.id.textName);
        textIngredient=view.findViewById(R.id.textIngredient);
        type=view.findViewById(R.id.textSearchType);
        btnSearch=view.findViewById(R.id.btnSearch);
        searchText=view.findViewById(R.id.textSreach);

        fullScreenImage = view.findViewById(R.id.fullScreenImage);
        textCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setText(textCategory.getText());
                btnSearch.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        mealsPresenter=new SearchMealsPresenter(SearchFragment.this, Repository.getRepository(LocalDataSource.getInstance(getContext()), RemoteDBSource.getInstance()),searchText.getText().toString());

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

                        mealsPresenter=new SearchMealsPresenter(SearchFragment.this,Repository.getRepository(LocalDataSource.getInstance(getContext()),RemoteDBSource.getInstance()),searchText.getText().toString());

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

                        mealsPresenter=new SearchMealsPresenter(SearchFragment.this,Repository.getRepository(LocalDataSource.getInstance(getContext()),RemoteDBSource.getInstance()),searchText.getText().toString());

                        mealsPresenter.getMealsByName();
                    }
                });
                searchText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        filterNames(charSequence.toString());
                        mealsPresenter=new SearchMealsPresenter(SearchFragment.this,Repository.getRepository(LocalDataSource.getInstance(getContext()),RemoteDBSource.getInstance()),searchText.getText().toString());
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

                        mealsPresenter=new SearchMealsPresenter(SearchFragment.this,Repository.getRepository(LocalDataSource.getInstance(getContext()),RemoteDBSource.getInstance()),searchText.getText().toString());

                        mealsPresenter.getMealsByIngredient();
                    }
                });


            }
        });


        recyclerView = view.findViewById(R.id.recyclerViewSearchMeals);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mealsAdapter =new SearchMealsAdapter(this,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mealsAdapter);


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<Meal> meals) {

        if (meals != null) {
            // Hide the full screen image if it's visible
            if (fullScreenImage != null) {
                fullScreenImage.setVisibility(View.GONE);
            }

            // Show the RecyclerView and populate it with favorite meals
            recyclerView.setVisibility(View.VISIBLE);
            mealsNames=meals;
            mealsAdapter.setMeals(meals);
            mealsAdapter.notifyDataSetChanged();

        } else {
            // Show the full screen image if the list of products is null
            recyclerView.setVisibility(View.GONE);
            fullScreenImage.setVisibility(View.VISIBLE);
        }
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
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFavMealClickListener(Meal meal) {
        if (!MainActivity2.sharedPreferences.contains("email") || !MainActivity2.sharedPreferences.contains("password")) {

            showCreateAccountDialog();

        } else {

            mealsPresenter.addToFav(meal);
        }

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
    @Override
    public void onMealDetailsClickListener(String name) {
        Intent intent = new Intent(getContext(), MealActivity.class);
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