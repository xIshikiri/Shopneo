package com.example.shopneo.main.fragment;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.model.Item;
import com.example.shopneo.model.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ItemShowcaseFragment extends Fragment {

    public ItemShowcaseFragment() {
        // Required empty public constructor
    }

    Spinner spinner;
    ImageView photo;
    TextView name;
    TextView price;
    TextView description;
    Button addToCart;
    ImageButton less;
    ImageButton more;
    EditText quantity;
    Item item;
    SharedPreferences cart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.itemsFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_showcase, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.itemShowCaseSize);
        photo = view.findViewById(R.id.itemShowCaseImage);
        name = view.findViewById(R.id.itemShowCaseTitle);
        price = view.findViewById(R.id.itemShowCasePrice);
        description = view.findViewById(R.id.itemShowCaseDescription);
        addToCart = view.findViewById(R.id.itemShowCaseBuyButton);
        less = view.findViewById(R.id.itemShowCaseLess);
        more = view.findViewById(R.id.itemShowCaseMore);
        quantity = view.findViewById(R.id.itemShowCaseQuantity);
        cart = view.getContext().getSharedPreferences("cart", -1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.article_sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        SQLiteDatabase db = new Shopneo.ShopneoDBHelper(getContext()).getWritableDatabase();
        item = new Shopneo.ShopneoDBHelper(getContext()).getItem(db, getArguments().getLong("id"));
        Log.i("ItemShowcaseFragment", item.getPhoto());
        photo.setImageResource(getResources().getIdentifier(item.getPhoto(), "drawable", getContext().getPackageName()));
        name.setText(getResources().getIdentifier(item.getName(), "string", getContext().getPackageName()));
        price.setText(item.getPrice() + "");
        description.setText(getResources().getIdentifier(item.getDesc(), "string", getContext().getPackageName()));

        less.setOnClickListener(l -> {
                int q = Integer.parseInt(quantity.getText().toString());
                if (q > 1) {
                    quantity.setText((q - 1) + "");
                }
        });

        more.setOnClickListener(l -> {
            if(!quantity.getText().toString().equals("")) {
                int q = Integer.parseInt(quantity.getText().toString());
                quantity.setText((q + 1) + "");
            } else {
                quantity.setText("1");
            }
        });

        addToCart.setOnClickListener(l -> {
            if(quantity.getText().toString().equals("")) {
                quantity.setText("1");
            }
            Gson gson = new Gson();
            String json;
            OrderItem orderItem = new OrderItem(item, Integer.parseInt(quantity.getText().toString()), spinner.getSelectedItem().toString());
            List<OrderItem> orderItems = new ArrayList<>();
            Type orderItemsType = new TypeToken<ArrayList<OrderItem>>(){}.getType();
            if(cart.getString("cart", "") == "") {
                orderItems.add(orderItem);
                json = gson.toJson(orderItems);
                cart.edit().putString("cart", json).apply();
                Log.i("cart", "JSON Cart: " + json);
                Log.i("cart", "Cart: " + orderItems);
            } else {
                json = cart.getString("cart", "");
                Log.i("cart", json);
                orderItems = gson.fromJson(json, orderItemsType);
                for (OrderItem o : orderItems) {
                    if (o.getItem().getId() == item.getId() && o.getSize().equals(spinner.getSelectedItem().toString())) {
                        o.setCount(o.getCount() + Integer.parseInt(quantity.getText().toString()));
                        json = gson.toJson(orderItems);
                        cart.edit().putString("cart", json).apply();
                        Log.i("cart", "JSON Cart: " + json);
                        Log.i("cart", "Cart: " + orderItems);
                        return;
                    }
                }
                orderItems.add(orderItem);
                json = gson.toJson(orderItems);
                cart.edit().putString("cart", json).apply();
                Log.i("cart", "JSON Cart: " + json);
                Log.i("cart", "Cart: " + orderItems);
            }
        });
    }
}