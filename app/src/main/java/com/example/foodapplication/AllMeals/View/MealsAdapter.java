package com.example.foodapplication.AllMeals.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.R;
import com.example.foodapplication.home.view.IAllCategoriestView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Context context;
    private List<Meal> meals;

    private static final String TAG = "ListAdapter";



    public MealsAdapter(@NonNull IAllMealsView context) {
        this.context = (Context) context;
        this.meals = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyeclerView, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(recyeclerView.getContext());
        View v=inflater.inflate(R.layout.meal_item,recyeclerView,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }


    public void setMeals(List<Meal> meals) {
        if (meals != null) { // Check if the list is not null
            this.meals = meals;
        } else {
            this.meals.clear(); // Clear the list if it's null to avoid NullPointerException
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(meals.get(position).getStrMeal());
       Picasso.get().load(meals.get(position).getStrMealThumb()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;

            imageView=itemView.findViewById(R.id.imgMeal);
            name=itemView.findViewById(R.id.txtFoodName);

        }
    }
}
