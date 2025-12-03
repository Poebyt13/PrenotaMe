package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appprenotame.network.models.api.AuthService;
import com.example.appprenotame.network.models.response.UserData;
import com.example.appprenotame.network.models.response.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.appprenotame.network.RetrofitClient;

import com.example.appprenotame.R;

public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView goBackImageView = view.findViewById(R.id.buttonBackProfile);

        TextView nameTv = view.findViewById(R.id.textUserName);
        TextView locationTv = view.findViewById(R.id.textUserLocation);
        TextView descTv = view.findViewById(R.id.textUserDescription);
        ImageView profileLarge = view.findViewById(R.id.imageUserProfileLarge);

        goBackImageView.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        // se è passato un userId tramite bundle, recuperalo e chiama l'API
        Bundle args = getArguments();
        if (args != null && args.containsKey("userId")) {
            int userId = args.getInt("userId");
            fetchAndBindUser(userId, nameTv, locationTv, descTv, profileLarge);
        }

    }

    private void fetchAndBindUser(int userId, TextView nameTv, TextView locationTv, TextView descTv, ImageView profileLarge) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthService authService = retrofit.create(AuthService.class);

        authService.getUserById(userId).enqueue(new Callback<ApiResponse<UserData>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserData>> call, Response<ApiResponse<UserData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<UserData> body = response.body();
                    if (body.isSuccess()) {
                        UserData user = body.getData();
                        if (user != null) {
                            nameTv.setText(user.getUsername() != null ? user.getUsername() : "");
                            // non c'è campo location nel user: lasciare quella parte o usare description
                            locationTv.setText("");
                            descTv.setText(user.getDescription() != null ? user.getDescription() : "");
                            String photo = user.getPhoto();
                            if (photo != null && !photo.isEmpty()) {
                                Glide.with(requireContext()).load(photo).into(profileLarge);
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), body.getMessagge(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Errore server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserData>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Errore di rete: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }
}