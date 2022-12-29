package com.example.shopneo.main.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.main.adapter.OrderAdapter;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    private Shopneo.ShopneoDBHelper dbHelper;
    private SQLiteDatabase db;

    public HistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = this.requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        long accountID = sharedPreferences.getLong("accountID", -1);

        dbHelper = new Shopneo.ShopneoDBHelper(getContext());
        db = dbHelper.getWritableDatabase();

        recyclerView = view.findViewById(R.id.historyRecycler);
        recyclerView.setAdapter(new OrderAdapter(getContext(), db, accountID));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}