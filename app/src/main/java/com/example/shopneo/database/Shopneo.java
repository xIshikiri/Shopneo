package com.example.shopneo.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shopneo.R;
import com.example.shopneo.model.ClientData;
import com.example.shopneo.model.Item;
import com.example.shopneo.model.Order;
import com.example.shopneo.model.OrderItem;

import java.util.ArrayList;

public final class Shopneo {

    private Shopneo() {}
    public static final String SQL_TAG = "SQL";

    private static final String DATABASE_NAME = "Shopneo.db";
    private static final int DATABASE_VERSION = 1;

    public static class Account implements BaseColumns {
        private static final String ACCOUNT_TABLE_NAME = "Account";
        private static final String ACCOUNT_COLUMN_ID_CLIENT = "id_client"; // integer
        private static final String ACCOUNT_COLUMN_EMAIL = "email"; // text
        private static final String ACCOUNT_COLUMN_PASSWORD = "password"; // text
    }

    public static class Client implements BaseColumns {
        private static final String CLIENT_TABLE_NAME = "Client";
        private static final String CLIENT_COLUMN_NAME = "name"; // text
        private static final String CLIENT_COLUMN_SURNAME = "surname"; // text
        private static final String CLIENT_COLUMN_DOB = "date_of_birth"; // datetime
        private static final String CLIENT_COLUMN_PHONE = "phone_number"; // text
    }

    public static class Cart implements BaseColumns {
        private static final String ORDER_TABLE_NAME = "Orders";
        private static final String ORDER_COLUMN_ID_CLIENT = "id_client"; // integer
        private static final String ORDER_COLUMN_DATE = "date"; // datetime
        private static final String ORDER_COLUMN_HOME_ADDRESS = "home_address"; // text
        private static final String ORDER_COLUMN_CITY = "city"; // text
        private static final String ORDER_COLUMN_POSTAL_CODE = "postal_code"; // text
    }

    public static class OrderProduct implements BaseColumns {
        private static final String ORDER_PRODUCT_TABLE_NAME = "OrderProduct";
        private static final String ORDER_PRODUCT_COLUMN_ID_ORDER = "id_order"; // integer, primary key, unique, auto increment
        private static final String ORDER_PRODUCT_COLUMN_ID_PRODUCT = "id_product"; // integer
        private static final String ORDER_PRODUCT_COLUMN_SIZE = "size"; // text
        private static final String ORDER_PRODUCT_COLUMN_COUNT = "count"; // integer
    }

    public static class Product implements BaseColumns {
        private static final String PRODUCT_TABLE_NAME = "Product";
        private static final String PRODUCT_COLUMN_NAME = "name"; // text
        private static final String PRODUCT_COLUMN_DESC = "description"; // text
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
                    + Cart.ORDER_COLUMN_POSTAL_CODE + " TEXT NOT NULL);";

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
                    + Product.PRODUCT_COLUMN_DESC + " TEXT NOT NULL, "
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

    public static class ShopneoDBHelper extends SQLiteOpenHelper {

        public final Context context;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Shopneo.db";

        public ShopneoDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        public Context getContext() {
            return context;
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

        private void importData(SQLiteDatabase db) {
            TypedArray article_names = context.getResources().obtainTypedArray(R.array.article_names);
            TypedArray article_desc = context.getResources().obtainTypedArray(R.array.article_desc);
            TypedArray article_photos = context.getResources().obtainTypedArray(R.array.article_photos);
            TypedArray article_prices = context.getResources().obtainTypedArray(R.array.article_prices);
            TypedArray article_types = context.getResources().obtainTypedArray(R.array.article_types);

            for (int i = 0; i < article_names.length(); i++) {

                String[] imgPath = String.valueOf(article_photos.getText(i)).split("/");
                String img = imgPath[imgPath.length - 1].replace(".png", "").trim();

                db.execSQL("INSERT INTO Product(name, description, photo, price, type) VALUES ('"
                        + article_names.getText(i) + "', '"
                        + article_desc.getText(i) + "', '"
                        + img + "', "
                        + article_prices.getFloat(i, -1) + ", '"
                        + article_types.getText(i) + "');");
            }
            db.execSQL("INSERT INTO Client(name, surname, date_of_birth, phone_number) VALUES ('"
                    + "John" + "', '"
                    + "Doe" + "', '"
                    + "1990-01-01" + "', '"
                    + "123456789" + "');");
            db.execSQL("INSERT INTO Account(email, password, id_client) VALUES ('admin@gmail.com', 'admin', 1);");

            article_names.recycle();
            article_desc.recycle();
            article_photos.recycle();
            article_prices.recycle();
            article_types.recycle();
        }

        public Item[] getItemList(SQLiteDatabase db) {
            Cursor cursor = db.rawQuery("SELECT * FROM Product", null);
            Item[] items = new Item[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                items[i] = new Item(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getFloat(4),
                        cursor.getString(5)
                );
                i++;
            }
            cursor.close();
            return items;
        }

        public Item getItem(SQLiteDatabase db, long id) {
            Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE _id = " + id, null);
            Item item = null;
            if (cursor.moveToNext()) {
                item = new Item(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getFloat(4),
                        cursor.getString(5)
                );
            }
            cursor.close();
            return item;
        }

        public ArrayList<OrderItem> getOrderItems(SQLiteDatabase db, long orderID) {
            Cursor cursor = db.rawQuery("SELECT * FROM OrderProduct WHERE id_order = " + orderID, null);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            int i = 0;
            while (cursor.moveToNext()) {
                orderItems.add(new OrderItem(
                        cursor.getInt(1),
                        getItem(db, cursor.getInt(2)),
                        cursor.getInt(3),
                        cursor.getString(4)
                ));
                Log.i("getOrderItems", orderItems.get(i).toString());
                i++;
            }
            cursor.close();
            return orderItems;
        }

        public Order[] getOrders(SQLiteDatabase db, long clientID) {
            Cursor cursor = db.rawQuery("SELECT * FROM Orders WHERE id_client = " + clientID, null);
            Order[] orders = new Order[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                orders[i] = new Order(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        getOrderItems(db, cursor.getInt(0)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        getClientData(db, cursor.getInt(1)).getName() + " " + getClientData(db, cursor.getInt(1)).getSurname()
                );
                Log.i("getOrder", orders[i].toString());
                i++;
            }
            cursor.close();
            return orders;
        }

        public ClientData getClientData(SQLiteDatabase db, long clientID) {
            Cursor cursor = db.rawQuery("SELECT * FROM Client WHERE _id = " + clientID, null);
            ClientData clientData = null;
            if (cursor.moveToNext()) {
                clientData = new ClientData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
            }
            cursor.close();
            return clientData;
        }

        public void insertOrder(SQLiteDatabase db, Order order){
            ContentValues values = new ContentValues();
            values.put("id_client", order.getClientID());
            values.put("date", order.getDate());
            values.put("home_address", order.getHomeAddress());
            values.put("city", order.getCity());
            values.put("postal_code", order.getPostalCode());
            long orderID = db.insert("Orders", null, values);
            Log.i("cart", "Order Data: " + values.toString());

            for (OrderItem orderItem : order.getItems()) {
                values = new ContentValues();
                values.put("id_order", orderID);
                values.put("id_product", orderItem.getItem().getId());
                values.put("count", orderItem.getCount());
                values.put("size", orderItem.getSize());
                db.insert("OrderProduct", null, values);
            }
            Log.i("cart", "Order item: " + values.toString());
        };
    }
}
