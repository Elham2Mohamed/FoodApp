package com.example.foodapplication.filter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Model.Categories;
import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.Model.Repository;
import com.example.foodapplication.R;
import com.example.foodapplication.home.view.IAllCategoriestView;
import com.example.foodapplication.home.view.OnCategoriesClickListener;
import com.example.foodapplication.network.RemoteDBSource;
import com.example.foodapplication.search.controller.SearchMealsPresenter;
import com.example.foodapplication.search.view.SearchActivity2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilterTypeAdapter extends RecyclerView.Adapter<FilterTypeAdapter.ViewHolder> {
    private static Context context;

    private static List<Meal> values;
    static OnFilterClickListener listener;
    private static final String TAG = "ListAdapter";



    public FilterTypeAdapter(@NonNull IFilterMealsView context, OnFilterClickListener listener) {
        this.context = (Context) context;
        this.values = new ArrayList<>();
        this.listener=listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyeclerView, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(recyeclerView.getContext());
        View v=inflater.inflate(R.layout.filter_item,recyeclerView,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }


    public void setValues(List<Meal> values) {
        if (values != null) { // Check if the list is not null
            this.values = values;
        } else {
            this.values.clear(); // Clear the list if it's null to avoid NullPointerException
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(FilterMealsActivity.type ==0){
            holder.name.setText(values.get(position).getStrCategory());
           // type=holder.name.getText().toString();
        }
        else if(FilterMealsActivity.type==1){
          holder.name.setText(values.get(position).getStrIngredient());
            //type=holder.name.getText().toString();
        }
        else{
            holder.name.setText(values.get(position).getStrArea());
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onFilterClickListener(holder.name.getText().toString());
                 }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ConstraintLayout constraintLayout;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            name = itemView.findViewById(R.id.txtFilter);
            constraintLayout = itemView.findViewById(R.id.row2);
        }

    }
}
