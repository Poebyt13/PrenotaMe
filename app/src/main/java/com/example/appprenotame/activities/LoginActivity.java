package com.example.appprenotame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.appprenotame.network.User;
import com.example.appprenotame.network.UserSession;
import com.example.appprenotame.network.models.api.AuthService;
import com.example.appprenotame.network.models.request.LoginRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView SignUpButton = findViewById(R.id.textSpan_SignUp);
        SignUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
            startActivity(intent);
        });

        Button bottone = findViewById(R.id.buttonLogin);
        bottone.setOnClickListener(v -> {
            validateForm();
        });

    }
    private void validateForm() {
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (emailText.isEmpty()) {
            email.setError("Email required!");
            return;
        }
        if (passwordText.isEmpty()) {
            password.setError("Password required!");
            return;
        }

        AuthService authService = RetrofitClient.getClient().create(AuthService.class);
        LoginRequest request = new LoginRequest(emailText, passwordText);

        authService.login(request).enqueue(new Callback<ApiResponse<LoginData>>() {
            @Override
            public void onResponse(Call<ApiResponse<LoginData>> call, Response<ApiResponse<LoginData>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ApiResponse<LoginData> body = response.body();

                    if (response.body().isSuccess()) {
                        LoginData loginData = body.getData();
                        User user = new User(
                                loginData.getId(),
                                loginData.getUsername(),
                                loginData.getEmail(),
                                loginData.getDescription()
                        );
                        UserSession.getInstance().setUser(user);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        Toast.makeText(LoginActivity.this, "Login successful!" , Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, body.getMessagge(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Errore server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<LoginData>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "Errore di rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}