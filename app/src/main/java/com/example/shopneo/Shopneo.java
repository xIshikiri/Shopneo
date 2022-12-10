package com.example.shopneo;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Shopneo {

    private Shopneo() {}
    public static final String SQL_TAG = "SQL";

    private static final String DATABASE_NAME = "Shopneo.db";
    private static final int DATABASE_VERSION = 1;

    public static class Account implements BaseColumns {
        private static final String ACCOUNT_TABLE_NAME = "Account";
//        private static final String ACCOUNT_COLUMN_ID = "id_account"; // integer, primary key, unique, auto increment
        private static final String ACCOUNT_COLUMN_ID_CLIENT = "id_client"; // integer
        private static final String ACCOUNT_COLUMN_EMAIL = "email"; // text
        private static final String ACCOUNT_COLUMN_PASSWORD = "password"; // text
    }

    public static class Client implements BaseColumns {
        private static final String CLIENT_TABLE_NAME = "Client";
//        private static final String CLIENT_COLUMN_ID = "id_client"; // integer, primary key, unique, auto increment
        private static final String CLIENT_COLUMN_NAME = "name"; // text
        private static final String CLIENT_COLUMN_SURNAME = "surname"; // text
        private static final String CLIENT_COLUMN_DOB = "date_of_birth"; // datetime
        private static final String CLIENT_COLUMN_PHONE = "phone_number"; // text
    }

    public static class Cart implements BaseColumns {
        private static final String ORDER_TABLE_NAME = "Cart";
//        private static final String ORDER_COLUMN_ID = "id_order"; // integer, primary key, unique, auto increment
        private static final String ORDER_COLUMN_ID_CLIENT = "id_client"; // integer
        private static final String ORDER_COLUMN_DATE = "date"; // datetime
        private static final String ORDER_COLUMN_HOME_ADDRESS = "home_address"; // text
        private static final String ORDER_COLUMN_CITY = "city"; // text
        private static final String ORDER_COLUMN_POSTAL_CODE = "postal_code"; // text
    }

    public static class OrderProduct implements BaseColumns {
        private static final String ORDER_PRODUCT_TABLE_NAME = "Order_Product";
        private static final String ORDER_PRODUCT_COLUMN_ID_ORDER = "id_order"; // integer, primary key, unique, auto increment
        private static final String ORDER_PRODUCT_COLUMN_ID_PRODUCT = "id_product"; // integer
        private static final String ORDER_PRODUCT_COLUMN_SIZE = "size"; // text
        private static final String ORDER_PRODUCT_COLUMN_COUNT = "count"; // integer
    }

    public static class Product implements BaseColumns {
        private static final String PRODUCT_TABLE_NAME = "Product";
//        private static final String PRODUCT_COLUMN_ID = "id_product"; // integer, primary key, unique, auto increment
        private static final String PRODUCT_COLUMN_NAME = "name"; // text
        private static final String PRODUCT_COLUMN_PRICE = "price"; // real
        private static final String PRODUCT_COLUMN_TYPE = "type"; // text
        private static final String PRODUCT_COLUMN_PHOTO = "photo"; // text
    }

    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE IF NOT EXISTS "
                + Account.ACCOUNT_TABLE_NAME + " ("
                + Account._ID + " INTEGER PRIMARY KEY, "
                + Account.ACCOUNT_COLUMN_ID_CLIENT + " INTEGER NOT NULL, "
                + Account.ACCOUNT_COLUMN_EMAIL + " TEXT NOT NULL, "
                + Account.ACCOUNT_COLUMN_PASSWORD + " TEXT NOT NULL);";

    private static final String SQL_CREATE_CLIENT =
            "CREATE TABLE IF NOT EXISTS "
                + Client.CLIENT_TABLE_NAME + " ("
                + Client._ID + " INTEGER PRIMARY KEY, "
                + Client.CLIENT_COLUMN_NAME + " TEXT NOT NULL, "
                + Client.CLIENT_COLUMN_SURNAME + " TEXT NOT NULL, "
                + Client.CLIENT_COLUMN_DOB + " TEXT NOT NULL, "
                + Client.CLIENT_COLUMN_PHONE + " TEXT NOT NULL);";

    private static final String SQL_CREATE_ORDER =
            "CREATE TABLE IF NOT EXISTS "
                + Cart.ORDER_TABLE_NAME + " ("
                + Cart._ID + " INTEGER PRIMARY KEY, "
                + Cart.ORDER_COLUMN_ID_CLIENT + " INTEGER NOT NULL, "
                + Cart.ORDER_COLUMN_DATE + " TEXT NOT NULL, "
                + Cart.ORDER_COLUMN_HOME_ADDRESS + " TEXT NOT NULL, "
                + Cart.ORDER_COLUMN_CITY + " TEXT NOT NULL, "
                + Cart.ORDER_COLUMN_POSTAL_CODE + "TEXT NOT NULL);";

    private static final String SQL_CREATE_ORDER_PRODUCT =
            "CREATE TABLE IF NOT EXISTS "
                + OrderProduct.ORDER_PRODUCT_TABLE_NAME + " ("
                + OrderProduct._ID + " INTEEGER PRIMARY KEY, "
                + OrderProduct.ORDER_PRODUCT_COLUMN_ID_ORDER + " INTEGER NOT NULL, "
                + OrderProduct.ORDER_PRODUCT_COLUMN_ID_PRODUCT + " INTEGER NOT NULL, "
                + OrderProduct.ORDER_PRODUCT_COLUMN_COUNT + " INTEGER NOT NULL, "
                + OrderProduct.ORDER_PRODUCT_COLUMN_SIZE + " TEXT NOT NULL);";

    private static final String SQL_CREATE_PRODUCT =
            "CREATE TABLE IF NOT EXISTS "
                + Product.PRODUCT_TABLE_NAME + " ("
                + Product._ID + " INTEGER PRIMARY KEY, "
                + Product.PRODUCT_COLUMN_NAME + " TEXT NOT NULL, "
                + Product.PRODUCT_COLUMN_TYPE + " TEXT NOT NULL, "
                + Product.PRODUCT_COLUMN_PRICE + " REAL NOT NULL, "
                + Product.PRODUCT_COLUMN_PHOTO + " TEXT NOT NULL);";

    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + Account.ACCOUNT_TABLE_NAME + ";";

    private static final String SQL_DELETE_CLIENT =
            "DROP TABLE IF EXISTS " + Client.CLIENT_TABLE_NAME + ";";

    private static final String SQL_DELETE_ORDER =
            "DROP TABLE IF EXISTS " + Cart.ORDER_TABLE_NAME + ";";

    private static final String SQL_DELETE_ORDER_PRODUCT =
            "DROP TABLE IF EXISTS " + OrderProduct.ORDER_PRODUCT_TABLE_NAME + ";";

    private static final String SQL_DELETE_PRODUCT =
            "DROP TABLE IF EXISTS " + Product.PRODUCT_TABLE_NAME + ";";

    public static class ShopneoDBHelper extends SQLiteOpenHelper{

        private final Context context;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Shopneo.db";

        public ShopneoDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ACCOUNT);
            sqLiteDatabase.execSQL(SQL_CREATE_CLIENT);
            sqLiteDatabase.execSQL(SQL_CREATE_ORDER);
            sqLiteDatabase.execSQL(SQL_CREATE_ORDER_PRODUCT);
            sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT);

            importData(sqLiteDatabase);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_ACCOUNT);
            sqLiteDatabase.execSQL(SQL_DELETE_CLIENT);
            sqLiteDatabase.execSQL(SQL_DELETE_ORDER);
            sqLiteDatabase.execSQL(SQL_DELETE_ORDER_PRODUCT);
            sqLiteDatabase.execSQL(SQL_DELETE_PRODUCT);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        private void importData(SQLiteDatabase db){
            List<String> article_names = Arrays.asList(context.getResources().getStringArray(R.array.article_names));
            TypedArray article_photos = context.getResources().obtainTypedArray(R.array.article_photos);
            TypedArray article_prices = context.getResources().obtainTypedArray(R.array.article_prices);
            List<String> article_types = Arrays.asList(context.getResources().getStringArray(R.array.article_types));

            for (int i = 0; i < article_names.size(); i++){
                db.execSQL("INSERT INTO Product(name, photo, price, type) VALUES ('"
                        + article_names.get(i) + "', "
                        + article_photos.getResourceId(i, -1) + ", "
                        + article_prices.getFloat(i, -1) + ", '"
                        + article_types.get(i) + "');");
            }

            article_photos.recycle();
            article_prices.recycle();
        }
    }
}
