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

public class CartViewHolder extends RecyclerView.Adapter<CartViewHolder.ViewHolder> {

    List<Order> carts  = new ArrayList<>();
    Context mContext;

    public CartViewHolder(Context context, List<Order> carts) {
        this.carts = carts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_cart_item_view,parent,false);
        return new ViewHolder(view);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textFoodName;
        TextView textFoodPrice;
        ImageView imageFoodCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.cart_item_name);
            textFoodPrice = itemView.findViewById(R.id.cart_item_price);
            imageFoodCount = itemView.findViewById(R.id.cart_item_count);
        }

    }

}
