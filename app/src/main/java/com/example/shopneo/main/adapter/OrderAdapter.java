package com.example.shopneo.main.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.model.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Order[] orders;
    Context context;
    SQLiteDatabase db;

    public OrderAdapter(Context context, SQLiteDatabase db, long accountID) {
        this.db = db;
        this.orders = new Shopneo.ShopneoDBHelper(context).getOrders(db, accountID);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId;
        TextView orderAddress;
        TextView orderName;
        RecyclerView orderItems;
        TextView orderTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderID);
            orderAddress = itemView.findViewById(R.id.orderAddress);
            orderName = itemView.findViewById(R.id.orderName);
            orderItems = itemView.findViewById(R.id.orderItems);
            orderTotal = itemView.findViewById(R.id.orderTotal);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_full, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderId.setText(orders[position].getId() + " ");
        holder.orderAddress.setText(orders[position].getHomeAddress() + ", " + orders[position].getCity() + ", " + orders[position].getPostalCode());
        holder.orderName.setText(orders[position].getName());
        holder.orderItems.setAdapter(new OrderElementAdapter(context, db, orders[position].getId()));
        holder.orderItems.setLayoutManager(new LinearLayoutManager(context));
        holder.orderTotal.setText(String.format("%.02f", orders[position].getTotal()) + " zł");
    }

    @Override
    public int getItemCount() {
        return orders.length;
    }

}
