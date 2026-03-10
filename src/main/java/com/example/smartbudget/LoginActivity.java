package com.example.smartbudget;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton, cancelButton;
    TextView messageText;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database
        db = new DatabaseHelper(this);

        // Connect XML Elements
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        cancelButton = findViewById(R.id.cancelButton);
        messageText = findViewById(R.id.messageText);

        // LOGIN BUTTON CLICK
        loginButton.setOnClickListener(v -> loginUser());

        // CANCEL BUTTON CLICK
        cancelButton.setOnClickListener(v -> {
            username.setText("");
            password.setText("");
            messageText.setVisibility(View.GONE);
        });
    }

    // ==============================
    // LOGIN METHOD
    // ==============================
    private void loginUser() {

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {

            messageText.setText("Please enter username and password");
            messageText.setVisibility(View.VISIBLE);
            return;
        }

        // ✅ Admin Login (Optional Professional Feature)
        if (user.equals("admin") && pass.equals("admin123")) {

            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
            finish();
            return;
        }

        // ✅ Check User from Database
        boolean isValid = db.checkUser(user, pass);

        if (isValid) {

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            // Go to Dashboard
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("USERNAME", user);
            startActivity(intent);
            finish();

        } else {

            messageText.setText("Invalid Username or Password");
            messageText.setVisibility(View.VISIBLE);
        }
    }
}