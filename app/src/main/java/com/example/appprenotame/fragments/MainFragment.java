package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appprenotame.R;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isAdmin = true; // Dovremmo determinare questo valore in base allo stato di accesso dell'utente
        ImageButton plus = view.findViewById(R.id.buttonPlus);
        plus.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_container_view, new AdminCreateEvent())
                    .addToBackStack(null)
                    .commit();
        });

        TextView titolo = view.findViewById(R.id.textViewTitleHome);
        LinearLayout titoloWithAdminOptions = view.findViewById(R.id.linearLayoutTitleHome);

        if (isAdmin) {
            titoloWithAdminOptions.setVisibility(View.VISIBLE);
        } else {
            titolo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}