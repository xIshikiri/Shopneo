package com.example.shopneo.main.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.model.Order;
import com.example.shopneo.model.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    ArrayList<OrderItem> orderItems;
    Context context;
    SharedPreferences cart;
    String json;
    Gson gson;

    public CartAdapter(Context context) {
        this.context = context;
        cart = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
        json = cart.getString("cart", "");
        gson = new Gson();
        Type orderItemsType = new TypeToken<ArrayList<OrderItem>>(){}.getType();
        orderItems = gson.fromJson(json, orderItemsType);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.cartItemName.setText("[" + orderItems.get(position).getSize() + "] " + context.getString(context.getResources().getIdentifier(orderItems.get(position).getItem().getName(), "string", context.getPackageName())));
        float price = orderItems.get(position).getItem().getPrice() * orderItems.get(position).getCount();
        holder.cartItemPrice.setText(String.format("%.02f", price) + " zł");
        holder.cartItemCount.setText(orderItems.get(position).getCount() + "");
        holder.cartItemLess.setOnClickListener(l -> {
            if(orderItems.get(position).getCount() > 1) {
                orderItems.get(position).setCount(orderItems.get(position).getCount() - 1);

                json = gson.toJson(orderItems);
                cart.edit().putString("cart", json).apply();

                Log.i("JSON cart", cart.getString("cart", ""));
                Log.i("cart", orderItems.toString());

                holder.cartItemCount.setText(orderItems.get(position).getCount() + "");
                holder.cartItemPrice.setText(String.format("%.02f", orderItems.get(position).getItem().getPrice() * orderItems.get(position).getCount()) + " zl");
            } else {
                orderItems.remove(position);
                json = gson.toJson(orderItems);
                cart.edit().putString("cart", json).apply();
                notifyDataSetChanged();
            }
        });

        holder.cartItemMore.setOnClickListener(l -> {
            orderItems.get(position).setCount(orderItems.get(position).getCount() + 1);

            json = gson.toJson(orderItems);
            cart.edit().putString("cart", json).apply();

            Log.i("JSON cart", cart.getString("cart", ""));
            Log.i("cart", orderItems.toString());

            holder.cartItemCount.setText(orderItems.get(position).getCount() + "");
            holder.cartItemPrice.setText(String.format("%.02f", orderItems.get(position).getItem().getPrice() * orderItems.get(position).getCount()) + " zl");
        });

        holder.cartItemDelete.setOnClickListener(l -> {
            orderItems.remove(position);
            json = gson.toJson(orderItems);
            cart.edit().putString("cart", json).apply();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cartItemName;
        TextView cartItemPrice;
        ImageButton cartItemLess;
        TextView cartItemCount;
        ImageButton cartItemMore;
        ImageButton cartItemDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            cartItemLess = itemView.findViewById(R.id.cartItemLess);
            cartItemCount = itemView.findViewById(R.id.cartItemCount);
            cartItemMore = itemView.findViewById(R.id.cartItemMore);
            cartItemDelete = itemView.findViewById(R.id.cartItemDelete);
        }
    }
}
