package com.example.appprenotame.fragments;

import android.content.Intent;
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

import com.example.appprenotame.R;

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
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_container_view , new WelcomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_complete_profile, container, false);
    }


}