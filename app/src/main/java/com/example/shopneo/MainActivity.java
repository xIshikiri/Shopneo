package com.example.shopneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Shopneo.ShopneoDBHelper dbHelper;
    SQLiteDatabase db;
    Button button;
    ImageView imageView;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new Shopneo.ShopneoDBHelper(this);
        db = dbHelper.getWritableDatabase();

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.example_photo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        button.setOnClickListener(l -> {
            String e = email.getText().toString();
            String p = password.getText().toString();
            Log.i("db", e + " " + p);
            Cursor cursor = db.query("Account", new String[]{"email", "password"}, "email=? AND password=?", new String[]{e, p}, null, null, null);
            Log.i("db", String.valueOf(cursor.getCount()));
            if (cursor.getCount() == 1){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });

    }
}