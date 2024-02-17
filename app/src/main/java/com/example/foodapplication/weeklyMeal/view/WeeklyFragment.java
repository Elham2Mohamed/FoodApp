package com.example.foodapplication.weeklyMeal.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Meal.View.MealActivity;
import com.example.foodapplication.Model.LocalDataSource;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.db.MealEntry;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.weeklyMeal.controller.CalMealPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class WeeklyFragment extends Fragment implements OnCalClickListener, CalMealView {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    WeeklyAdapter weeklyAdapter;
    CalMealPresenter calMealPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewFavorite);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        calMealPresenter =new CalMealPresenter(this , Repository.getRepository(LocalDataSource.getInstance(getContext()), RemoteDBSource.getInstance()));


        weeklyAdapter = new WeeklyAdapter(this, new ArrayList<>(), WeeklyFragment.this);
        recyclerView.setAdapter(weeklyAdapter);
        calMealPresenter.getCalMeal();
    }

    @SuppressLint({"CheckResult", "NotifyDataSetChanged"})
    @Override
    public void showData(Flowable<List<MealEntry>> products) {
        products.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            weeklyAdapter.setValues(item);
                            weeklyAdapter.notifyDataSetChanged();
                        }
                );

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDeleteCalClickListener(MealEntry meal) {
        calMealPresenter.deleteFromCal(meal);
        weeklyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMealDetailsClickListener(String name) {
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra("mealName", name);
        startActivity(intent);
    }
}