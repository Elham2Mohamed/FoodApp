package com.example.foodapplication.Meal.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.foodapplication.R;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Model.MealIngredients;

import java.util.List;

public class MealIngredientsAdapter  extends RecyclerView.Adapter<MealIngredientsAdapter.ViewHolder> {

    private List<MealIngredients> ingredientsList;

    public void setIngredientsList(List<MealIngredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealIngredients ingredient = ingredientsList.get(position);
        holder.ingredientTextView.setText(ingredient.getIngredient());
        holder.measureTextView.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredientsList == null ? 0 : ingredientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        TextView measureTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.ingredientTextView);
            measureTextView = itemView.findViewById(R.id.measureTextView);
        }
    }
}