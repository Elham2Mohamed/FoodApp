package com.example.foodapplication.searchFragment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchMealsAdapter extends RecyclerView.Adapter<SearchMealsAdapter.ViewHolder> {
    private ISearchMealsView context;
    private List<Meal> meals;
    static OnSearchClickListener listener;
    private static final String TAG = "ListAdapter";



    public SearchMealsAdapter(@NonNull ISearchMealsView context, OnSearchClickListener listener) {
        this.context =  context;
        this.meals = new ArrayList<>();
         this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyeclerView, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(recyeclerView.getContext());
        View v=inflater.inflate(R.layout.meal_item,recyeclerView,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }


    public void setMeals(List<Meal> meals) {
        if (meals != null) {
            this.meals = meals;
        } else {
            this.meals.clear();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(meals.get(position).getStrMeal());
       Picasso.get().load(meals.get(position).getStrMealThumb()).into(holder.imageView);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealDetailsClickListener(meals.get(position).getStrMeal());
            }
        });
        holder.btnAddFAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavMealClickListener(meals.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;
        public View layout;
        ImageButton btnAddFAV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;
            btnAddFAV=itemView.findViewById(R.id.imgfav);
            imageView=itemView.findViewById(R.id.imgMeal);
            name=itemView.findViewById(R.id.txtFoodName);

        }
    }
}
