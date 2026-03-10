package com.example.smartbudget;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    Button btnViewUsers, btnViewExpenses, btnDeleteAll;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = new DatabaseHelper(this);

        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);

        // View Users
        btnViewUsers.setOnClickListener(v -> showUsers());

        // View Expenses
        btnViewExpenses.setOnClickListener(v -> showExpenses());

        // Delete All Data
        btnDeleteAll.setOnClickListener(v -> {
            db.getWritableDatabase().execSQL("DELETE FROM users");
            db.getWritableDatabase().execSQL("DELETE FROM expenses");

            Toast.makeText(this, "All Data Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    // ==========================================
    // SHOW USERS
    // ==========================================
    private void showUsers() {

        Cursor cursor = db.getReadableDatabase()
                .rawQuery("SELECT * FROM users", null);

        if (cursor.getCount() == 0) {
            showMessage("Users", "No Users Found");
            return;
        }

        StringBuilder buffer = new StringBuilder();

        while (cursor.moveToNext()) {
            buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
            buffer.append("Name: ").append(cursor.getString(1)).append("\n");
            buffer.append("Email: ").append(cursor.getString(2)).append("\n");
            buffer.append("Phone: ").append(cursor.getString(3)).append("\n");
            buffer.append("Username: ").append(cursor.getString(4)).append("\n\n");
        }

        showMessage("Users List", buffer.toString());
        cursor.close();
    }

    // ==========================================
    // SHOW EXPENSES
    // ==========================================
    private void showExpenses() {

        Cursor cursor = db.getReadableDatabase()
                .rawQuery("SELECT * FROM expenses", null);

        if (cursor.getCount() == 0) {
            showMessage("Expenses", "No Expenses Found");
            return;
        }

        StringBuilder buffer = new StringBuilder();

        while (cursor.moveToNext()) {
            buffer.append("ID: ").append(cursor.getInt(0)).append("\n");
            buffer.append("Amount: ").append(cursor.getDouble(1)).append("\n");
            buffer.append("Category: ").append(cursor.getString(2)).append("\n");
            buffer.append("Date: ").append(cursor.getString(3)).append("\n\n");
        }

        showMessage("Expenses List", buffer.toString());
        cursor.close();
    }

    // ==========================================
    // ALERT DIALOG TO DISPLAY DATA
    // ==========================================
    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}