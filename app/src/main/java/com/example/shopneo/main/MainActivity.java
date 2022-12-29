package com.example.shopneo.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.shopneo.R;
import com.example.shopneo.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    NavController navController;
    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        long accountID = sharedPreferences.getLong("accountID", -1);
        Log.i("db", "ID: " + accountID);
        if (accountID == -1){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                sharedPreferences.edit().remove("accountID").apply();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuOrders:
                navController.navigate(R.id.historyFragment);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}