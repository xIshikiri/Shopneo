package com.example.shopneo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.main.MainActivity;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {


    Shopneo.ShopneoDBHelper dbHelper;
    SQLiteDatabase db;
    Button button;
    EditText email;
    EditText password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new Shopneo.ShopneoDBHelper(this);
        db = dbHelper.getWritableDatabase();

        button = findViewById(R.id.login_button);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        button.setOnClickListener(l -> {
            TextView title = findViewById(R.id.login_title);
            String e = email.getText().toString();
            String p = password.getText().toString();
            Log.i("db", e + " " + p);
            Cursor cursor = db.query("Account", new String[]{"_id", "email", "password"}, "email=? AND password=?", new String[]{e, p}, null, null, null);
            Log.i("db", String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            if (cursor.getCount() == 1){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("accountID", cursor.getString(0));
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

    }
}