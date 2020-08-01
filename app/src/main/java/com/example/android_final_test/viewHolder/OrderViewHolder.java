package com.example.android_final_test.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final_test.R;
import com.example.android_final_test.model.Request;

import java.util.ArrayList;
import java.util.List;

public class OrderViewHolder extends RecyclerView.Adapter<OrderViewHolder.ViewHolder> {

    List<Request> requests  = new ArrayList<>();
    Context mContext;

    public OrderViewHolder(Context context, List<Request> requests) {
        this.requests = requests;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_order_status_item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderStatus.setText(checkStatus(requests.get(position).getStatus()));
        holder.orderAddress.setText(requests.get(position).getAddress());
        holder.orderPhone.setText(requests.get(position).getPhone());
        holder.orderName.setText(requests.get(position).getName());
    }

    private String checkStatus(String status) {
        if(status.equals("0"))
            return "PLACED";
        else if(status.equals("1"))
            return "ON THE WAY";
        else if(status.equals("2"))
            return "ARRIVED";
        else
            return "SHIPPED";
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderStatus, orderPhone, orderAddress, orderName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderPhone = itemView.findViewById(R.id.order_phone);
            orderAddress = itemView.findViewById(R.id.order_address);
            orderName = itemView.findViewById(R.id.order_name);
        }
    }

}
