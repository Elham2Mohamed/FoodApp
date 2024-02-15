package com.example.foodapplication.Favorite.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.Model.Meal;
import com.example.foodapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
@NonNull
private Context context;
private List<Meal> values;
private OnMealClickListener listener;
private static final String TAG = "ListAdapter";



    public FavoriteAdapter(@NonNull FavoriteActivity context, List<Meal> values, OnMealClickListener listener) {
        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyeclerView, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(recyeclerView.getContext());
        View v=inflater.inflate(R.layout.favourite_item,recyeclerView,false);
        ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
        }


    @SuppressLint("NotifyDataSetChanged")
    public void setValues(List<Meal> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Name.setText(values.get(position).getStrMeal());
        if(holder.imageView != null) {
            Picasso.get().load(values.get(position).getStrMealThumb()).into(holder.imageView);
        }
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteFavClickListener(values.get(position));
            }
        });
    }

@Override
public int getItemCount() {
        return values.size();
        }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Cat, Area;
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
        }
    }

}
