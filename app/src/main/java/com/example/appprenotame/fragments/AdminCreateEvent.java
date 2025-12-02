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
import com.example.appprenotame.network.models.api.UtilsService;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.CategoriesData;
import com.example.appprenotame.network.models.response.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCreateEvent extends Fragment {



    public AdminCreateEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_create_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView arrowLeft = view.findViewById(R.id.arrow_indietro);
        arrowLeft.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        EditText titleField = view.findViewById(R.id.create_event_titleField);
        EditText descriptionField = view.findViewById(R.id.create_event_descriptionField);
        EditText dataStartField = view.findViewById(R.id.dataInizio);
        EditText dataEndField = view.findViewById(R.id.dataFine);
        Spinner categoryField = view.findViewById(R.id.spinner);
        allCategories(categoryField);
        EditText seatsNumberField = view.findViewById(R.id.seatsNumber);
        EditText positionField = view.findViewById(R.id.posizione);
        EditText urlField = view.findViewById(R.id.urlImmagine);
        Button botnetSubmit = view.findViewById(R.id.submitButton);

        botnetSubmit.setOnClickListener(v -> {
            String titleText = titleField.getText().toString();
            String descriptionText = descriptionField.getText().toString();
            String dataStartText = dataStartField.getText().toString();
            String dataFineText = dataEndField.getText().toString();
            String categoryText = categoryField.getSelectedItem().toString();
            String seatsNumber = seatsNumberField.getText().toString();
            String positionText = positionField.getText().toString();
            String urlText = urlField.getText().toString();

            if (titleText.isEmpty() || descriptionText.isEmpty() || dataStartText.isEmpty() ||
            dataFineText.isEmpty() || categoryText.isEmpty() || seatsNumber.isEmpty()
            || positionText.isEmpty() || urlText.isEmpty()) {
                Toast.makeText(getContext() , "Compila tutti i campi" , Toast.LENGTH_LONG).show();
                return;
            }
        });
    }


    private List<String> allCategories(Spinner categoryField) {
        UtilsService cli = RetrofitClient.getClient().create(UtilsService.class);
        List<Category> categorie = new ArrayList<Category>();
        cli.getCategories().enqueue(new Callback<ApiResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                Log.d("response" , response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Category>> body = response.body();
                    if (body.isSuccess()) {
                        List<Category> listaCategorie = body.getData();
                        if (!listaCategorie.isEmpty()) {
                            List<String> nomi = new ArrayList<String>();
                            for (Category c : listaCategorie) {
                                nomi.add(c.getName());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext() ,
                                    android.R.layout.simple_spinner_item ,
                                    nomi);

                            categoryField.setAdapter(adapter);
                        }
                    }
            }
                else {
                    Toast.makeText(getContext() , "Server error" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                Toast.makeText(getContext() , "Errore di rete: " + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
        return categorie.stream().map(Category::getName).toList();
    }
}