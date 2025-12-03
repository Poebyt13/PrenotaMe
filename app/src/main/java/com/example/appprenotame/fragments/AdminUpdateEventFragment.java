package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appprenotame.R;
import com.example.appprenotame.network.RetrofitClient;
import com.example.appprenotame.network.models.api.EventService;
import com.example.appprenotame.network.models.api.UtilsService;
import com.example.appprenotame.network.models.request.UpdateEventRequest;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.Category;
import com.example.appprenotame.network.models.response.DeleteEventData;
import com.example.appprenotame.network.models.response.EventData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUpdateEventFragment extends Fragment {

    private int eventId;
    private Spinner categorySpinner;
    private ArrayAdapter<String> categoryAdapter;

    public AdminUpdateEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_update_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey("eventId")) {
            Toast.makeText(getContext(), "ID evento non fornito", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
            return;
        }

        eventId = getArguments().getInt("eventId", -1);

        ImageView arrowBack = view.findViewById(R.id.update_arrow_indietro);
        arrowBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        Button buttonDelete = view.findViewById(R.id.deleteButton);
        buttonDelete.setOnClickListener(v -> {
            deleteEvent();
        });


        EditText titleField = view.findViewById(R.id.update_event_titleField);
        EditText descriptionField = view.findViewById(R.id.update_event_descriptionField);
        EditText dateStartField = view.findViewById(R.id.dataInizio);
        EditText dateEndField = view.findViewById(R.id.dataFine);
        categorySpinner = view.findViewById(R.id.spinner);
        EditText seatsField = view.findViewById(R.id.updateSeatsNumber);
        EditText locationField = view.findViewById(R.id.posizione);
        EditText imageUrlField = view.findViewById(R.id.updateUrlImmagine);
        Button submitButton = view.findViewById(R.id.submitButton);

        Button updateButton = view.findViewById(R.id.updateSubmitButton);
        updateButton.setOnClickListener(v -> {
            String title = titleField.getText().toString();
            String description = descriptionField.getText().toString();
            String imageUrl = imageUrlField.getText().toString();

            updateEvent(title, description, imageUrl);
        });

        // l'adapter -> lista vuota
        categoryAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new ArrayList<>());
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        EventService eventService = RetrofitClient.getClient().create(EventService.class);

        eventService.getEventById(eventId).enqueue(new Callback<ApiResponse<EventData>>() {
            @Override
            public void onResponse(Call<ApiResponse<EventData>> call, Response<ApiResponse<EventData>> response) {
                Log.d("response EVENT", "Response: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<EventData> body = response.body();
                    if (body.isSuccess()) {
                        EventData event = body.getData();

                        if (event == null) {
                            Toast.makeText(getContext(), "Evento non trovato", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d("EVENT", "Titolo: " + event);
                        titleField.setText(event.getTitle());
                        descriptionField.setText(event.getDescription());
                        dateStartField.setText(event.getDate_start().toString());
                        dateEndField.setText(event.getDate_end().toString());
                        seatsField.setText(String.valueOf(event.getSeats_total()));
                        locationField.setText(event.getLocation());
                        imageUrlField.setText(event.getImage_url());

                        // Carico la categoria dell'evento nel spinner
                        loadCategory(event.getCategory_id());
                    } else {
                        Toast.makeText(getContext(), body.getMessagge(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Errore nella risposta dal server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<EventData>> call, Throwable t) {
                Log.e("NetworkError", "Errore di rete: ", t);
                Toast.makeText(getContext(), "Errore di rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCategory(int categoryId) {
        UtilsService utilsService = RetrofitClient.getClient().create(UtilsService.class);

        utilsService.getCategoryById(categoryId).enqueue(new Callback<ApiResponse<Category>>() {
            @Override
            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Category> body = response.body();
                    if (body.isSuccess()) {
                        Category category = body.getData();

                        // la categoria dello spinner
                        setCategoryInSpinner(category.getName());
                    } else {
                        Toast.makeText(getContext(), body.getMessagge(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Errore server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Errore di rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCategoryInSpinner(String categoryName) {
        categoryAdapter.clear();
        categoryAdapter.add(categoryName);
        categoryAdapter.notifyDataSetChanged();
        categorySpinner.setSelection(0);
    }

    private void deleteEvent() {
        EventService eventService = RetrofitClient.getClient().create(EventService.class);

        eventService.deleteEvent(eventId).enqueue(new Callback<ApiResponse<DeleteEventData>>() {
            @Override
            public void onResponse(Call<ApiResponse<DeleteEventData>> call, Response<ApiResponse<DeleteEventData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<DeleteEventData> body = response.body();
                    if (body.isSuccess()) {
                        Toast.makeText(getContext(), "Evento eliminato con successo", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), body.getMessagge(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Errore server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DeleteEventData>> call, Throwable t) {
                Toast.makeText(getContext(), "Errore di rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEvent(String title, String description, String imageUrl) {

        EventService eventService = RetrofitClient.getClient().create(EventService.class);
        UpdateEventRequest updateRequest = new UpdateEventRequest(title, description, imageUrl);

        eventService.updateEvent(eventId, updateRequest).enqueue(new Callback<ApiResponse<EventData>>() {
            @Override
            public void onResponse(Call<ApiResponse<EventData>> call, Response<ApiResponse<EventData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<EventData> body = response.body();
                    if (body.isSuccess()) {
                        Toast.makeText(getContext(), "Evento aggiornato con successo", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), body.getMessagge(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Errore server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<EventData>> call, Throwable t) {
                Toast.makeText(getContext(), "Errore di rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
