package com.example.android_final_test.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final_test.R;

import com.example.android_final_test.model.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodViewHolder extends RecyclerView.Adapter<FoodViewHolder.ViewHolder> implements View.OnClickListener {

    public TextView textFoodName;
    public ImageView imageView;
    List<Food> foods  = new ArrayList<>();
    Context mContext;
    ItemClickListener itemClickListener;

    public FoodViewHolder(Context context, ArrayList<Food> foods, ItemClickListener itemClickListener) {
        this.foods = foods;
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_food_item_view,parent,false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textFoodName.setText(foods.get(position).getName());
        Picasso.with(mContext).load(foods.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textFoodName;
        ImageView imageView;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.foodName);
            imageView = itemView.findViewById(R.id.foodImage);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClick(int position);
    }
}
