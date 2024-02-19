package com.example.foodapplication.filterFragment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.R;
import com.example.foodapplication.filterFragment.view.FilterFragment;

import java.util.ArrayList;
import java.util.List;

public class FilterTypeAdapter extends RecyclerView.Adapter<FilterTypeAdapter.ViewHolder> {
    private static FilterFragment context;

    private static List<Meal> values;
    static OnFilterClickListener listener;
    private static final String TAG = "ListAdapter";



    public FilterTypeAdapter(@NonNull FilterFragment context, OnFilterClickListener listener) {
        this.context =  context;
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
        if (values != null) {
            this.values = values;
        } else {
            this.values.clear();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(FilterFragment.type ==0){
            holder.name.setText(values.get(position).getStrCategory());
           // type=holder.name.getText().toString();
        }
        else if(FilterFragment.type==1){
          holder.name.setText(values.get(position).getStrIngredient());

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
