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
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.appprenotame.network.models.api.AuthService;
import com.example.appprenotame.network.models.response.UserData;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.api.EventService;
import com.example.appprenotame.network.models.response.EventData;
import com.example.appprenotame.network.models.EventAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.appprenotame.network.RetrofitClient;

import com.example.appprenotame.R;
import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView goBackImageView = view.findViewById(R.id.buttonBackProfile);

        TextView nameTv = view.findViewById(R.id.textUserName);
        TextView emailTv = view.findViewById(R.id.textUserEmail);
        TextView roleTv = view.findViewById(R.id.textUserRole);
        ImageView roleIcon = view.findViewById(R.id.iconUserRole);
        View roleRow = view.findViewById(R.id.roleRow);
        TextView createdAtTv = view.findViewById(R.id.textUserCreatedAt);
        TextView descTv = view.findViewById(R.id.textUserDescription);
        ImageView profileLarge = view.findViewById(R.id.imageUserProfileLarge);

        // Spinner per scegliere la vista (Created / Partecipated)
        android.widget.Spinner spinner = view.findViewById(R.id.spinner_example);
        ListView listUserEvents = view.findViewById(R.id.listUserEvents);
        TextView emptyUserEvents = view.findViewById(R.id.emptyUserEvents);

        List<EventData> createdEvents = new ArrayList<>();
        List<EventData> joinedEvents = new ArrayList<>();

        EventAdapter createdAdapter = new EventAdapter(this, createdEvents);
        EventAdapter joinedAdapter = new EventAdapter(this, joinedEvents);

        // adapter di default mostra gli eventi creati
        listUserEvents.setAdapter(createdAdapter);
        listUserEvents.setEmptyView(emptyUserEvents);

        goBackImageView.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        // recupera userId dagli argomenti
        Bundle args = getArguments();
        if (args != null && args.containsKey("userId")) {

            // prendo l'id dell'utente dagli argomenti
            int userId = args.getInt("userId");
            fetchAndBindUser(userId, nameTv, emailTv, roleTv, createdAtTv, descTv, profileLarge, roleRow);
            loadUserEvents(userId, createdAdapter, joinedAdapter);

            spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, View viewSel, int position, long idSel) {
                    String sel = (String) parent.getItemAtPosition(position);
                    if (sel != null && sel.toLowerCase().contains("creat")) {
                        listUserEvents.setAdapter(createdAdapter);
                    } else {
                        listUserEvents.setAdapter(joinedAdapter);
                    }
                }

                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {
                    // no-op
                }
            });
        }

    }

    private void fetchAndBindUser(int userId, TextView nameTv, TextView emailTv, TextView roleTv, TextView createdAtTv, TextView descTv, ImageView profileLarge, View roleRow) {
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
                            emailTv.setText(user.getEmail() != null ? user.getEmail() : "");
                            // ruolo
                            if (user.getIs_admin() == 1) {
                                roleTv.setText("Amministratore dell'app");
                                roleRow.setVisibility(View.VISIBLE);
                            } else {
                                roleRow.setVisibility(View.GONE);
                            }
                            createdAtTv.setText(user.getCreated_at() != null ? user.getCreated_at() : "");
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

    private void loadUserEvents(int userId, EventAdapter createdAdapter, EventAdapter joinedAdapter) {
        Retrofit retrofit = RetrofitClient.getClient();
        EventService eventService = retrofit.create(EventService.class);

        eventService.getEventsByCreator(userId).enqueue(new retrofit2.Callback<ApiResponse<List<EventData>>>() {
            @Override
            public void onResponse(retrofit2.Call<ApiResponse<List<EventData>>> call, retrofit2.Response<ApiResponse<List<EventData>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<EventData>> body = response.body();
                    if (body.isSuccess() && body.getData() != null) {
                        List<EventData> created = body.getData();
                        createdAdapter.clear();
                        createdAdapter.addAll(created);
                        createdAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ApiResponse<List<EventData>>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        eventService.getBookedEventsByUser(userId).enqueue(new retrofit2.Callback<ApiResponse<List<EventData>>>() {
            @Override
            public void onResponse(retrofit2.Call<ApiResponse<List<EventData>>> call, retrofit2.Response<ApiResponse<List<EventData>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<EventData>> body = response.body();
                    if (body.isSuccess() && body.getData() != null) {
                        List<EventData> joined = body.getData();
                        joinedAdapter.clear();
                        joinedAdapter.addAll(joined);
                        joinedAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ApiResponse<List<EventData>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}