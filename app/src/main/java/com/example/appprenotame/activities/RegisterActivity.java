package com.example.appprenotame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appprenotame.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView button = findViewById(R.id.textSpan_LogIn);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
            startActivity(intent);
        });

        Button bottone = findViewById(R.id.buttonSignUp);
        bottone.setOnClickListener(v -> {
            validateForm();
        });
    }


    private void validateForm() {
        EditText emailField = findViewById(R.id.editTextEmail);
        EditText passwordField = findViewById(R.id.editTextPassword);
        EditText repeatPasswordField = findViewById(R.id.editTextRepeatPassword);

        if (emailField.getText().toString().isEmpty()) {
            emailField.setError("Email required");
        }
        if (passwordField.getText().toString().isEmpty()) {
            passwordField.setError("Password required");
        }
        if (!repeatPasswordField.getText().toString().equals(passwordField.getText().toString())) {
            repeatPasswordField.setError("Password non combaciano");
        }

    }
}