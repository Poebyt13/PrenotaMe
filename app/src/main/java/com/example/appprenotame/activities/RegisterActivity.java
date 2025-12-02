package com.example.appprenotame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appprenotame.R;
import com.example.appprenotame.network.RetrofitClient;
import com.example.appprenotame.network.models.api.AuthService;
import com.example.appprenotame.network.models.request.RegisterRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.RegisterData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            return;
        }
        if (passwordField.getText().toString().isEmpty()) {
            passwordField.setError("Password required");
            return;
        }
        if (!repeatPasswordField.getText().toString().equals(passwordField.getText().toString())) {
            repeatPasswordField.setError("Password non combaciano");
            return;
        }

        AuthService authService = RetrofitClient.getClient().create(AuthService.class);
        RegisterRequest request = new RegisterRequest(emailField.getText().toString(), passwordField.getText().toString());

        authService.register(request).enqueue(new Callback<ApiResponse<RegisterData>>() {
            @Override
            public void onResponse(Call<ApiResponse<RegisterData>> call, Response<ApiResponse<RegisterData>> response) {
               if (response.isSuccessful() && response.body() != null) {
                   ApiResponse<RegisterData> body = response.body();
                   if (response.body().isSuccess()) {
                       Toast.makeText(RegisterActivity.this , "Registrazione effettuata con successo" , Toast.LENGTH_LONG).show();
                   }
                   else {
                       Toast.makeText(RegisterActivity.this , body.getMessagge() , Toast.LENGTH_LONG).show();
                   }
               }
               else {
                   Toast.makeText(RegisterActivity.this , "Errore server:" + response.code() , Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<ApiResponse<RegisterData>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this , "Errore di rete:" + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

    }
}
