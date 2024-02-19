package com.example.foodapplication.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.AllMeals.View.AllMealsActivity;
import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private static Context context;
    private static List<Categories> values;
    static OnCategoriesClickListener listener;
    private static final String TAG = "ListAdapter";



    public CategoriesAdapter(@NonNull IAllCategoriestView context,OnCategoriesClickListener listener) {
        this.context = (Context) context;
        this.values = new ArrayList<>();
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyeclerView, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(recyeclerView.getContext());
        View v=inflater.inflate(R.layout.categories_item,recyeclerView,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }


    public void setValues(List<Categories> values) {
        if (values != null) {
            this.values = values;
        } else {
            this.values.clear();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

       holder.name.setText(values.get(position).getStrCategory());

        Picasso.get().load(values.get(position).getStrCategoryThumb()).into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoriesClickListener(values.get(position).getStrCategory());
                 }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            imageView = itemView.findViewById(R.id.imgFood);
            name = itemView.findViewById(R.id.txtCatName);
            constraintLayout = itemView.findViewById(R.id.row2);
        }

    }
}
