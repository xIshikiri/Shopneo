package com.example.shopneo.login.fragment;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.example.shopneo.R;
import com.example.shopneo.database.Shopneo;
import com.example.shopneo.main.MainActivity;

public class RegisterFragment extends Fragment {

    Shopneo.ShopneoDBHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    EditText name;
    EditText surname;
    EditText dateOfBirth;
    EditText phoneNumber;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registerButton;
    Button loginInsteadButton;

    public RegisterFragment() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.registerName);
        surname = view.findViewById(R.id.registerSurname);
        dateOfBirth = view.findViewById(R.id.registerDoB);
        phoneNumber = view.findViewById(R.id.registerPhone);
        email = view.findViewById(R.id.registerEmail);
        password = view.findViewById(R.id.registerPassword);
        confirmPassword = view.findViewById(R.id.registerConfirmPassword);
        registerButton = view.findViewById(R.id.registerButton);
        loginInsteadButton = view.findViewById(R.id.loginInsteadButton);

        dbHelper = new Shopneo.ShopneoDBHelper(view.getContext());
        db = dbHelper.getWritableDatabase();

        sharedPreferences = view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        registerButton.setOnClickListener(l -> {

            String e = email.getText().toString();
            String p = password.getText().toString();
            Log.i("db", e + " " + p);
            Cursor cursor = db.query("Account", new String[]{"_id", "email"}, "email=?", new String[]{e}, null, null, null);
            Log.i("db", String.valueOf(cursor.getCount()));
            cursor.moveToFirst();

            if(name.getText().toString().isEmpty() || surname.getText().toString().isEmpty() || dateOfBirth.getText().toString().isEmpty() || phoneNumber.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()){
                Toast.makeText(view.getContext(), R.string.fillInAllFields, Toast.LENGTH_SHORT).show();
            } else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                Toast.makeText(view.getContext(), R.string.passwordsDontMatch, Toast.LENGTH_SHORT).show();
            } else if(cursor.getCount() > 0){
                Toast.makeText(view.getContext(), R.string.accountAlreadyExists, Toast.LENGTH_SHORT).show();
                cursor.close();
            } else {
                cursor.close();
                ContentValues values = new ContentValues();
                values.put("name", name.getText().toString());
                values.put("surname", surname.getText().toString());
                values.put("date_of_birth", dateOfBirth.getText().toString());
                values.put("phone_number", phoneNumber.getText().toString());
                long newRowId = db.insert("Client", null, values);
                values = new ContentValues();
                values.put("id_client", newRowId);
                values.put("email", email.getText().toString());
                values.put("password", password.getText().toString());
                long accountID = db.insert("Account", null, values);
                if(accountID != -1){
                    sharedPreferences.edit().putLong("accountID", accountID).apply();
                    Intent intent = new Intent(this.getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), R.string.emailAlreadyExists, Toast.LENGTH_SHORT).show();
                    db.delete("Client", "id_client = ?", new String[]{String.valueOf(newRowId)});
                }
            }
        });

        loginInsteadButton.setOnClickListener(l -> {
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        });
    }
}