package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appprenotame.R;
import com.example.appprenotame.activities.HomeActivity;
import com.example.appprenotame.activities.LoginActivity;
import com.example.appprenotame.network.RetrofitClient;
import com.example.appprenotame.network.User;
import com.example.appprenotame.network.UserSession;
import com.example.appprenotame.network.models.api.AuthService;
import com.example.appprenotame.network.models.request.CompleteProfileRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.CompleteProfileData;
import com.example.appprenotame.network.models.response.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileFragment extends Fragment {

    public CompleteProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button continueBotton = view.findViewById(R.id.continue_botton);

        continueBotton.setOnClickListener(v -> {
            EditText usernameField = view.findViewById(R.id.username_field);
            EditText descriptionField = view.findViewById(R.id.editTextDescrizione);

            if (usernameField.getText().toString().isEmpty()) {
                usernameField.setError("Username required");
                return;
            }
            if (descriptionField.getText().toString().isEmpty()) {
                descriptionField.setError("Description required");
                return;
            }


            User user = UserSession.getInstance().getUser();

            AuthService authService = RetrofitClient.getClient().create(AuthService.class);
            CompleteProfileRequest request = new CompleteProfileRequest(
                    user.getId(),
                    usernameField.getText().toString(),
                    descriptionField.getText().toString()
            );

            authService.completeProfile(request).enqueue(new Callback<ApiResponse<CompleteProfileData>>() {
                @Override
                public void onResponse(Call<ApiResponse<CompleteProfileData>> call, Response<ApiResponse<CompleteProfileData>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<CompleteProfileData> body = response.body();
                        if (body.isSuccess()) {
                            CompleteProfileData data = body.getData();
                            Toast.makeText(getContext(), "Profilo completato con successo!", Toast.LENGTH_SHORT).show();

                            getParentFragmentManager().beginTransaction()
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .replace(R.id.fragment_container_view , new WelcomeFragment())
                                    .addToBackStack(null)
                                    .commit();

                        } else {
                            Toast.makeText(getContext(),  body.getMessagge(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Errore server: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<CompleteProfileData>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Errore di rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_complete_profile, container, false);
    }


}