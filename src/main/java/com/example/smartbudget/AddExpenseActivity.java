package com.example.smartbudget;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etAmount, etCategory, etDate;
    Button btnSaveExpense;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        db = new DatabaseHelper(this);

        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etDate = findViewById(R.id.etDate);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        btnSaveExpense.setOnClickListener(view -> {

            String amountStr = etAmount.getText().toString();
            String category = etCategory.getText().toString();
            String date = etDate.getText().toString();

            if(amountStr.isEmpty() || category.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);

            boolean inserted = insertExpense(amount, category, date);

            if(inserted) {
                Toast.makeText(this, "Expense Saved Successfully", Toast.LENGTH_SHORT).show();
                etAmount.setText("");
                etCategory.setText("");
                etDate.setText("");
            } else {
                Toast.makeText(this, "Failed to Save Expense", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean insertExpense(double amount, String category, String date) {
        return db.insertExpense(amount, category, date);
    }
}