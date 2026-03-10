package com.example.smartbudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnAddExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAddExpense = findViewById(R.id.btnAddExpense);

        btnAddExpense.setOnClickListener(view ->
                startActivity(new Intent(this, AddExpenseActivity.class))
        );
    }
}