package com.example.shopneo.main.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.model.Order;
import com.example.shopneo.model.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public class OrderFragment extends Fragment {

    ArrayList<OrderItem> orderItems;
    SharedPreferences cart;
    SharedPreferences loggedInAs;
    String json;
    Gson gson;
    Order order;
    EditText homeAddress;
    EditText city;
    EditText postalCode;
    Button orderButton;
    SQLiteDatabase db;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.cartFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cart = requireActivity().getSharedPreferences("cart", Context.MODE_PRIVATE);
        gson = new Gson();
        json = cart.getString("cart", null);
        Type type = new TypeToken<ArrayList<OrderItem>>(){}.getType();
        orderItems = gson.fromJson(json, type);

        loggedInAs = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        long accountID = loggedInAs.getLong("accountID", -1);

        homeAddress = view.findViewById(R.id.orderHomeAddress);
        city = view.findViewById(R.id.orderCity);
        postalCode = view.findViewById(R.id.orderPostalCode);
        orderButton = view.findViewById(R.id.orderButton);
        db = new Shopneo.ShopneoDBHelper(getContext()).getWritableDatabase();

        orderButton.setOnClickListener(l -> {
            if(!(orderItems == null) && !(homeAddress.getText().toString().equals("")) && !(city.getText().toString().equals("")) && !(postalCode.getText().toString().equals(""))) {
                order = new Order(accountID, orderItems, new Timestamp(System.currentTimeMillis()).toString(), homeAddress.getText().toString(), city.getText().toString(), postalCode.getText().toString());
                new Shopneo.ShopneoDBHelper(getContext()).insertOrder(db, order);
                cart.edit().clear().apply();
                Navigation.findNavController(view).navigate(R.id.itemsFragment);
            } else {
                Toast.makeText(getContext(), R.string.cartEmpty, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.itemsFragment);
            }
        });
    }
}