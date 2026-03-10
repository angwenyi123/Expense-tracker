package com.example.smartbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "SmartBudget.db";
    private static final int DATABASE_VERSION = 1;

    // ================= USERS TABLE =================
    private static final String TABLE_USERS = "users";

    // ================= EXPENSE TABLE =================
    private static final String TABLE_EXPENSES = "expenses";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ===============================================
    // CREATE TABLES
    // ===============================================
    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS TABLE
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT UNIQUE," +
                "phone TEXT," +
                "username TEXT," +
                "password TEXT)");

        // EXPENSE TABLE
        db.execSQL("CREATE TABLE " + TABLE_EXPENSES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "amount REAL," +
                "category TEXT," +
                "date TEXT)");
    }

    // ===============================================
    // DATABASE UPGRADE
    // ===============================================
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        onCreate(db);
    }

    // ===============================================
    // INSERT USER (Signup)
    // ===============================================
    public boolean insertUser(String name,
                              String email,
                              String phone,
                              String username,
                              String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("username", username);
        values.put("password", password);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    // ===============================================
    // CHECK USER LOGIN
    // ===============================================
    public boolean checkUser(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE username=? AND password=?",
                new String[]{username, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    // ===============================================
    // INSERT EXPENSE
    // ===============================================
    public boolean insertExpense(double amount,
                                 String category,
                                 String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("amount", amount);
        values.put("category", category);
        values.put("date", date);

        long result = db.insert(TABLE_EXPENSES, null, values);

        return result != -1;
    }

    // ===============================================
    // GET ALL EXPENSES (For RecyclerView Later)
    // ===============================================
    public Cursor getAllExpenses() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_EXPENSES +
                        " ORDER BY id DESC",
                null
        );
    }

    // ===============================================
    // GET TOTAL EXPENSE
    // ===============================================
    public double getTotalExpense() {

        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;

        Cursor cursor = db.rawQuery(
                "SELECT SUM(amount) FROM " + TABLE_EXPENSES,
                null
        );

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();
        return total;
    }
}