package com.example.foodapplication.weeklyMeal.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodapplication.R;
import com.example.foodapplication.db.MealEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder> {
@NonNull
private WeeklyFragment context;
private List<MealEntry> values;
private OnCalClickListener listener;
private static final String TAG = "ListAdapter";



    public WeeklyAdapter(@NonNull WeeklyFragment context, List<MealEntry> values, OnCalClickListener listener) {
        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyeclerView, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(recyeclerView.getContext());
        View v=inflater.inflate(R.layout.weekly_item,recyeclerView,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
        }


    @SuppressLint("NotifyDataSetChanged")
    public void setValues(List<MealEntry> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Name.setText(values.get(position).getName());
        holder.day.setText(values.get(position).getDate());
        holder.time.setText(values.get(position).getTime());
        if(holder.imageView != null) {
            Picasso.get().load(values.get(position).getImage()).into(holder.imageView);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealDetailsClickListener(values.get(position).getName());
            }
        });
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteCalClickListener(values.get(position));
            }
        });
    }

@Override
public int getItemCount() {
        return values.size();
        }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, day, time;
        public ImageView imageView;
        public View layout;
        ImageButton removeProduct;

        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            imageView = itemView.findViewById(R.id.imgFood);
            removeProduct = itemView.findViewById(R.id.btnDelete);
            Name = itemView.findViewById(R.id.txtMealName);
            day = itemView.findViewById(R.id.txtDay);
            time = itemView.findViewById(R.id.txtTime);
        }
    }

}
