package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        EditText seatsNumberField = view.findViewById(R.id.seatsNumber);
        EditText positionField = view.findViewById(R.id.posizione);
        EditText urlField = view.findViewById(R.id.urlImmagine);
        Button botnetSubmit = view.findViewById(R.id.submitButton);

        ArrayAdapter<String> categorie = new ArrayAdapter<>(getContext() , android.R.layout.simple_spinner_item , new String[]{"item1" , "item2" , "item3"});
        categorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryField.setAdapter(categorie);


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
}