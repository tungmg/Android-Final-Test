package com.example.android_final_test.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.android_final_test.R;
import com.example.android_final_test.model.Food;
import com.example.android_final_test.model.Order;
import com.example.android_final_test.view.Cart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartViewHolder extends RecyclerView.Adapter<CartViewHolder.ViewHolder> implements View.OnClickListener{

    List<Order> carts  = new ArrayList<>();
    Context mContext;
    ItemClickListener itemClickListener;

    public CartViewHolder(Context context, List<Order> carts, ItemClickListener itemClickListener) {
        this.carts = carts;
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
        View view = inflater.inflate(R.layout.activity_cart_item_view,parent,false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textFoodName.setText(carts.get(position).getProductName());
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound("" + carts.get(position).getQuantity(), Color.RED);
        holder.imageFoodCount.setImageDrawable(textDrawable);

        Locale locale = new Locale("vi", "VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(carts.get(position).getPrice()))*(Integer.parseInt(carts.get(position).getQuantity()));
        holder.textFoodPrice.setText(fmt.format(price));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textFoodName;
        TextView textFoodPrice;
        ImageView imageFoodCount;
        ImageView imageDelete;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.cart_item_name);
            textFoodPrice = itemView.findViewById(R.id.cart_item_price);
            imageFoodCount = itemView.findViewById(R.id.cart_item_count);
            imageDelete = itemView.findViewById(R.id.removeItem);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    itemClickListener.onDeleteClick(position);
                }
            });
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
