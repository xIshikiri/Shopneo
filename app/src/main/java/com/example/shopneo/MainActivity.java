package com.example.shopneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Shopneo.ShopneoDBHelper dbHelper;
    SQLiteDatabase db;
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new Shopneo.ShopneoDBHelper(this);
        db = dbHelper.getWritableDatabase();

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.example_photo);

        button.setOnClickListener(l -> {
            TypedArray images = getResources().obtainTypedArray(R.array.article_photos);
            imageView.setImageResource(images.getResourceId(new Random().nextInt(3), -1));
            images.recycle();
        });

    }
}