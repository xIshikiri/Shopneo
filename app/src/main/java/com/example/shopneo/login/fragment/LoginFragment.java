package com.example.shopneo.login.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.main.MainActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {

    Shopneo.ShopneoDBHelper dbHelper;
    SQLiteDatabase db;
    Button loginButton;
    Button registerInsteadButton;
    EditText email;
    EditText password;
    SharedPreferences sharedPreferences;

    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new Shopneo.ShopneoDBHelper(view.getContext());
        db = dbHelper.getWritableDatabase();

        loginButton = view.findViewById(R.id.loginButton);
        registerInsteadButton = view.findViewById(R.id.registerInsteadButton);
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(l -> {
            String e = email.getText().toString();
            String p = password.getText().toString();
            Log.i("db", e + " " + p);
            Cursor cursor = db.query("Account", new String[]{"_id", "email", "password"}, "email=? AND password=?", new String[]{e, p}, null, null, null);
            Log.i("db", String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            if (cursor.getCount() == 1){
                Toast.makeText(view.getContext(), "Correct", Toast.LENGTH_SHORT).show();
                sharedPreferences = this.requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                sharedPreferences.edit().putLong("accountID", cursor.getLong(0)).apply();
                Intent intent = new Intent(this.getContext(), MainActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this.getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

        registerInsteadButton.setOnClickListener(l -> {
            Navigation.findNavController(view).navigate(R.id.registerFragment);
        });

    }
}