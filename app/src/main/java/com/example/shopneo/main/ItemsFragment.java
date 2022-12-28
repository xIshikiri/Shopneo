package com.example.shopneo.main;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.main.adapter.Item;
import com.example.shopneo.main.adapter.ItemAdapter;

public class ItemsFragment extends Fragment {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private RecyclerView recyclerView;

    public ItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dbHelper = new Shopneo.ShopneoDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        Item[] items = new Shopneo.ShopneoDBHelper(getContext()).getItemList(db);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new ItemAdapter(items, getContext()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
}