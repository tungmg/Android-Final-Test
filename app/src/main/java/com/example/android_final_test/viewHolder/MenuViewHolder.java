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
import com.example.android_final_test.model.Category;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class MenuViewHolder extends RecyclerView.Adapter<MenuViewHolder.ViewHolder> implements View.OnClickListener {

    public TextView textCategoryName;
    public ImageView imageView;
    List<Category> categories = new ArrayList<>();
    Context mContext;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(Context context, ArrayList<Category> categories, ItemClickListener itemClickListener) {
        this.categories = categories;
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_category_item_view,parent,false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textCategoryName.setText(categories.get(position).getName());
        Picasso.with(mContext).load(categories.get(position).getImage())
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textCategoryName;
        ImageView imageView;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            textCategoryName = itemView.findViewById(R.id.categoryName);
            imageView = itemView.findViewById(R.id.categoryImage);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }

    @Override
    public void onClick(View v) {
//        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public interface ItemClickListener {
        void onClick(int position);
    }
}
