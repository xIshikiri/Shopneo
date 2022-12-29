package com.example.shopneo.main.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.model.OrderItem;

public class OrderElementAdapter extends RecyclerView.Adapter<OrderElementAdapter.ViewHolder> {

    OrderItem[] items;
    Context context;

    public OrderElementAdapter(Context context, SQLiteDatabase db, long orderID) {
        this.context = context;
        this.items = new Shopneo.ShopneoDBHelper(context).getOrderItems(db, orderID);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.orderElement);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_element, parent, false);
        return new OrderElementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(items[position].toString());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

}
