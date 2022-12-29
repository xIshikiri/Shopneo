package com.example.shopneo.main.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Item[] items;
    private Context context;

    public ItemAdapter(Context context, SQLiteDatabase db) {
        this.context = context;
        this.items = new Shopneo.ShopneoDBHelper(context).getItemList(db);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemPhoto;
        private TextView itemTitle;
        private TextView itemCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPhoto = (ImageView) itemView.findViewById(R.id.itemPhoto);
            itemTitle = (TextView) itemView.findViewById(R.id.itemName);
            itemCost = (TextView) itemView.findViewById(R.id.itemPrice);
        }

    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.itemPhoto.setImageResource(context.getResources().getIdentifier(items[position].getPhoto(), "drawable", context.getPackageName()));
        holder.itemTitle.setText(context.getResources().getIdentifier(items[position].getName(), "string", context.getPackageName()));
        holder.itemCost.setText(items[position].getPrice() + " zł");
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

}
