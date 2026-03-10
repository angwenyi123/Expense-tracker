package com.example.smartbudget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    // XML Elements
    EditText name, email, phoneNo, username, password;
    Button signupButton, cancelButton;
    TextView messageText;

    // Database
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize database
        db = new DatabaseHelper(this);

        // Connect XML Elements
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNo);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signupButton = findViewById(R.id.signupButton);
        cancelButton = findViewById(R.id.cancelButton);
        messageText = findViewById(R.id.messageText);

        // Signup button click
        signupButton.setOnClickListener(v -> registerUser());

        // Cancel button click
        cancelButton.setOnClickListener(v -> {
            clearFields();
            finish(); // Close activity
        });
    }

    // ==============================
    // REGISTER USER METHOD
    // ==============================
    private void registerUser() {

        String fullName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String phone = phoneNo.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(fullName) ||
                TextUtils.isEmpty(userEmail) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(userPassword)) {

            messageText.setText("Please fill all fields");
            messageText.setVisibility(View.VISIBLE);
            return;
        }

        // Insert into database
        boolean inserted = db.insertUser(
                fullName,
                userEmail,
                phone,
                userName,
                userPassword
        );

        if (inserted) {

            Toast.makeText(this,
                    "Registration Successful",
                    Toast.LENGTH_SHORT).show();

            messageText.setVisibility(View.GONE);
            clearFields();

        } else {

            messageText.setText("Registration Failed (Email might already exist)");
            messageText.setVisibility(View.VISIBLE);
        }
    }

    // ==============================
    // CLEAR INPUT FIELDS
    // ==============================
    private void clearFields() {

        name.setText("");
        email.setText("");
        phoneNo.setText("");
        username.setText("");
        password.setText("");
    }
}