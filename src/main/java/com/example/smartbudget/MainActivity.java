package com.example.smartbudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);


        btnLogin.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class))
        );

        // Go to Signup Screen
        btnSignup.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SignupActivity.class))
        );
    }
}